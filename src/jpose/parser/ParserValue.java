package jpose.parser;

import java.util.List;

import jpose.syntax.SyPrimitiveConstantBool;
import jpose.syntax.SyPrimitiveConstantInt;
import jpose.syntax.SyPrimitiveConstantSymbol;
import jpose.syntax.SyReferenceConstantLoc;
import jpose.syntax.SyReferenceConstantNull;
import jpose.syntax.SyReferenceConstantSymbol;
import jpose.syntax.SyValue;
import jpose.syntax.SyValuePrimitiveConstant;
import jpose.syntax.SyValueReferenceConstant;
import jpose.syntax.SyValueUnassumed;

public final class ParserValue implements Parser<SyValue> {
	@Override
	public ParseResult<SyValue> parse(List<String> tokens) {
		var pBOT = new ParserMatchToken("BOT");
		var b = new ParserBool();
		var n = new ParserInt();
		var X = new ParserSymbolPrimitive();
		var pnull = new ParserMatchToken("null");
		var l = new ParserLoc();
		var Y = new ParserSymbolReference();
		
		return  pBOT.transform(x -> (SyValue) new SyValueUnassumed()).alt(
		           b.transform(x -> (SyValue) new SyValuePrimitiveConstant(new SyPrimitiveConstantBool(x)))).alt(
		           n.transform(x -> (SyValue) new SyValuePrimitiveConstant(new SyPrimitiveConstantInt(x)))).alt(
		           X.transform(x -> (SyValue) new SyValuePrimitiveConstant(new SyPrimitiveConstantSymbol(x)))).alt(
		       pnull.transform(x -> (SyValue) new SyValueReferenceConstant(new SyReferenceConstantNull()))).alt(
		           l.transform(x -> (SyValue) new SyValueReferenceConstant(new SyReferenceConstantLoc(x)))).alt(
		           Y.transform(x -> (SyValue) new SyValueReferenceConstant(new SyReferenceConstantSymbol(x)))).parse(tokens);
	}
}
