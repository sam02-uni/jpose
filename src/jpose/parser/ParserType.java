package jpose.parser;

import java.util.List;

import jpose.syntax.SyType;
import jpose.syntax.SyTypeBool;
import jpose.syntax.SyTypeClass;
import jpose.syntax.SyTypeInt;

public final class ParserType implements Parser<SyType> {
	@Override
	public ParseResult<SyType> parse(List<String> tokens) {
		var pbool = new ParserMatchToken("bool");
		var pint = new ParserMatchToken("int");
		var identifier = new ParserIdentifier();
		
		return pbool.transform(_1 -> (SyType) new SyTypeBool()).alt(
		       pint.transform(_1 -> (SyType) new SyTypeInt())).alt(
		       identifier.transform(x -> (SyType) new SyTypeClass(x))).parse(tokens);
	}
}
