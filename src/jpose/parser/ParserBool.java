package jpose.parser;

import java.util.ArrayList;
import java.util.List;

import jpose.syntax.SyBool;
import jpose.syntax.SyBoolFalse;
import jpose.syntax.SyBoolTrue;

public final class ParserBool implements Parser<SyBool> {
	@Override
	public ParseResult<SyBool> parse(List<String> tokens) {
		if (tokens.isEmpty()) {
			return new ParseResult<>(tokens, OptionalError.error("Expected boolean, found end of stream"));
		} else {
			var token = tokens.getFirst();
			var tokensEtc = new ArrayList<>(tokens.subList(1, tokens.size()));
			if ("true".equals(token)) {
				return new ParseResult<>(tokensEtc, OptionalError.of(new SyBoolTrue()));
			} else if ("false".equals(token)) {
				return new ParseResult<>(tokensEtc, OptionalError.of(new SyBoolFalse()));
			} else {
				return new ParseResult<>(tokensEtc, OptionalError.error("Expected boolean, found " + token));
			}
		}
	}
}
