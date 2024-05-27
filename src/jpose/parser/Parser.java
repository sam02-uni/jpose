package jpose.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public interface Parser<T> {
	ParseResult<T> parse(List<String> tokens);
	
	default ParseResult<T> parse(String text) {
		var t = new Tokenizer();
		var l = t.tokenize(text);
		return parse(l);
	}
	
	//combinators
	
	default <U> Parser<U> andThen(Function<T, Parser<U>> f) {
		var p = this;
		return new Parser<U>() {
			@Override
			public ParseResult<U> parse(List<String> tokens) {
				var parseResult = p.parse(tokens);
				var toksNext = parseResult.tokensResidual();
				if (parseResult.parsed().isPresent()) {
					return f.apply(parseResult.parsed().get()).parse(toksNext);
				} else {
					return new ParseResult<U>(toksNext, OptionalError.error(parseResult.parsed().getErrorMessage()));
				}
			}
		};
	}
	
	default Parser<T> alt(Parser<T> q) {
		var p = this;
		return new Parser<T>() {
			@Override
			public ParseResult<T> parse(List<String> tokens) {
				var parseResult = p.parse(tokens);
				if (parseResult.parsed().isPresent()) {
					return parseResult;
				} else {
					return q.parse(tokens);
				}
			}
		};
	}
	
	default Parser<List<T>> star() {
		var p = this;
		return new Parser<List<T>>() {
			@Override
			public ParseResult<List<T>> parse(List<String> tokens) {
				return parseAux(tokens, new ArrayList<>());
			}
			
			private ParseResult<List<T>> parseAux(List<String> tokens, ArrayList<T> l) {
				var parseResult = p.parse(tokens);
				var toksNext = parseResult.tokensResidual();
				if (parseResult.parsed().isPresent()) {
					var t = parseResult.parsed().get();
					l.addFirst(t);
					return parseAux(toksNext, l);
				} else {
					return new ParseResult<List<T>>(tokens, OptionalError.of(new ArrayList<>(l.reversed())));
				}
			}
		};
	}
	
	default <U> Parser<U> transform(Function<T, U> f) {
		var p = this;
		return new Parser<U>() {
			@Override
			public ParseResult<U> parse(List<String> tokens) {
				var parseResult = p.parse(tokens);
				var toksNext = parseResult.tokensResidual();
				var parsed = parseResult.parsed();
				return new ParseResult<U>(toksNext, parsed.map(f));
			}
		};
	}
}
