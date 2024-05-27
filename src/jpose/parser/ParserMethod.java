package jpose.parser;

import java.util.List;

import jpose.syntax.SyDeclMethod;
import jpose.syntax.SyDeclMethodLit;
import jpose.syntax.SyDeclVariableLit;

public class ParserMethod implements Parser<SyDeclMethod> {
	@Override
	public ParseResult<SyDeclMethod> parse(List<String> tokens) {
		var type = new ParserType();
		var identifier = new ParserIdentifier();
		var expression = new ParserExpression();
		var pLeftParens = new ParserMatchToken("(");
		var pRightParens = new ParserMatchToken(")");
		var pAssign = new ParserMatchToken(":=");
		
		return type.andThen(type_m -> 
		         identifier.andThen(name_m -> 
		         pLeftParens.andThen(_1 -> 
		         type.andThen(type_p ->
		         identifier.andThen(name_p ->
		         pRightParens.andThen(_2 -> 
		         pAssign.andThen(_3 -> 
		         expression.transform(body -> (SyDeclMethod) new SyDeclMethodLit(type_m, name_m, new SyDeclVariableLit(type_p, name_p), body))))))))).parse(tokens);
	}
}
