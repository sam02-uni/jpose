package jpose.parser;

import java.util.ArrayList;
import java.util.List;

public final class ParserAnyToken implements Parser<String> {
	@Override
	public ParseResult<String> parse(List<String> tokens) {
		if (tokens.isEmpty()) {
			return new ParseResult<>(tokens, OptionalError.error("Expected a token, found end of stream"));
		} else {
			var token = tokens.getFirst();
			var tokensEtc = new ArrayList<>(tokens.subList(1, tokens.size()));
			return new ParseResult<>(tokensEtc, OptionalError.of(token));
		}
	}
}
