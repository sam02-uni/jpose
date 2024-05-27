package jpose.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ParserMatchToken implements Parser<String> {
	private final String s;
	
	public ParserMatchToken(String s) {
		Objects.requireNonNull(s);
		
		this.s = s;
	}
	
	@Override
	public ParseResult<String> parse(List<String> tokens) {
		if (tokens.isEmpty()) {
			return new ParseResult<>(tokens, OptionalError.error("Expected " + this.s + ", found end of stream"));
		} else {
			var token = tokens.getFirst();
			if (this.s.equals(token)) {
				var tokensEtc = new ArrayList<>(tokens.subList(1, tokens.size()));
				return new ParseResult<>(tokensEtc, OptionalError.of(token));
			} else {
				return new ParseResult<>(tokens, OptionalError.error("Expected " + this.s + ", found " + token));
			}
		}
	}
}
