package jpose.parser;

import java.util.ArrayList;
import java.util.List;

public class ParserCharPlusNatural implements Parser<Integer> {
	private char c;
	
	public ParserCharPlusNatural(char c) {
		this.c = c;
	}

	@Override
	public ParseResult<Integer> parse(List<String> tokens) {
		if (tokens.isEmpty()) {
			return new ParseResult<>(tokens, OptionalError.error("Expected char " + this.c + " plus natural number, found end of stream"));
		} else {
			var token = tokens.getFirst();
			var tokensEtc = new ArrayList<>(tokens.subList(1, tokens.size()));
			boolean success = true;
			if (token.length() >= 2 && token.charAt(0) == this.c) {
				try {
					Integer.parseInt(token.substring(1));
				} catch (NumberFormatException e) {
					success = false;
				}
			} else {
				success = false;
			}
			
			if (success) {
				return new ParseResult<>(tokensEtc, OptionalError.of(Integer.parseInt(token.substring(1))));
			} else {
				return new ParseResult<>(tokensEtc, OptionalError.error("Expected char " + this.c + " plus natural number, found " + token));
			}
		}
	}
}
