package jpose.parser;

import java.util.List;

import jpose.syntax.SyLocLit;

public final class ParserLoc implements Parser<SyLocLit> {
	@Override
	public ParseResult<SyLocLit> parse(List<String> tokens) {
		var p = new ParserCharPlusNatural('l');
		return p.transform(x -> new SyLocLit(x)).parse(tokens);
	}
}
