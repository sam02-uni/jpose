package jpose.parser;

import java.util.List;

import jpose.syntax.SySymbolExpression;

public final class ParserSymbolReference implements Parser<SySymbolExpression> {
	@Override
	public ParseResult<SySymbolExpression> parse(List<String> tokens) {
		var p = new ParserCharPlusNatural('Y');
		return p.transform(x -> new SySymbolExpression(x)).parse(tokens);
	}
}
