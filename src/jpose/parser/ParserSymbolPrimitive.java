package jpose.parser;

import java.util.List;

import jpose.syntax.SySymbolExpression;

public final class ParserSymbolPrimitive implements Parser<SySymbolExpression> {
	@Override
	public ParseResult<SySymbolExpression> parse(List<String> tokens) {
		var p = new ParserCharPlusNatural('X');
		return p.transform(x -> new SySymbolExpression(x)).parse(tokens);
	}
}
