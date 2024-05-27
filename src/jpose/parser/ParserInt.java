package jpose.parser;

import java.util.ArrayList;
import java.util.List;

import jpose.syntax.SyIntLit;

public class ParserInt implements Parser<SyIntLit> {

	@Override
	public ParseResult<SyIntLit> parse(List<String> tokens) {
		if (tokens.isEmpty()) {
			return new ParseResult<>(tokens, OptionalError.error("Expected natural number, found end of stream"));
		} else {
			var token = tokens.getFirst();
			var tokensEtc = new ArrayList<>(tokens.subList(1, tokens.size()));
			try {
				var n = Integer.parseInt(token);
				return new ParseResult<>(tokensEtc, OptionalError.of(new SyIntLit(n)));
			} catch (NumberFormatException e) {
				return new ParseResult<>(tokensEtc, OptionalError.error("Expected natural number, found " + token));
			}
		}
	}
}
