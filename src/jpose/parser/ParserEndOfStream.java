package jpose.parser;

import java.util.ArrayList;
import java.util.List;

public final class ParserEndOfStream implements Parser<Void> {
	@Override
	public ParseResult<Void> parse(List<String> tokens) {
		if (tokens.isEmpty()) {
			return new ParseResult<>(tokens, OptionalError.of());
		} else {
			var token = tokens.getFirst();
			var tokensEtc = new ArrayList<>(tokens.subList(1, tokens.size()));
			return new ParseResult<>(tokensEtc, OptionalError.error("Expected end of stream, found " + token));
		}
	}
}
