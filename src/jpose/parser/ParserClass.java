package jpose.parser;

import java.util.List;
import java.util.stream.Collectors;

import jpose.syntax.SyDeclClass;
import jpose.syntax.SyDeclClassLit;
import jpose.syntax.SyDeclVariable;
import jpose.syntax.SyDeclVariableLit;

public final class ParserClass implements Parser<SyDeclClass> {
	@Override
	public ParseResult<SyDeclClass> parse(List<String> tokens) {
		var identifier = new ParserIdentifier();
		var type = new ParserType();
		var method = new ParserMethod();
		var pclass = new ParserMatchToken("class");
		var pextends = new ParserMatchToken("extends");
		var pLeftCurlyBrace = new ParserMatchToken("{");
		var pRightCurlyBrace = new ParserMatchToken("}");
		var pSemicolon = new ParserMatchToken(";");

		return pclass.andThen(_1 -> 
		         identifier.andThen(name_c ->
		         pextends.andThen(_2 -> 
		         identifier).star().andThen(name_super -> //here we use star instead of alternative because we are lazy 
		         pLeftCurlyBrace.andThen(_3 -> 
		         type.andThen(type_fld ->
		         identifier.andThen(name_fld -> 
		         pSemicolon.transform(_4 -> (SyDeclVariable) new SyDeclVariableLit(type_fld, name_fld)))).star().andThen(flds ->
		         method.andThen(meth -> 
		         pSemicolon.transform(_5 -> meth)).star().andThen(meths -> 
		         pRightCurlyBrace.transform(_6 -> (SyDeclClass) new SyDeclClassLit(name_c, name_super.stream().collect(Collectors.joining()), flds, meths)))))))).parse(tokens);
	}
}
