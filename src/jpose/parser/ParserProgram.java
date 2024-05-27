package jpose.parser;

import java.util.List;

import jpose.syntax.SyProgram;
import jpose.syntax.SyProgramLit;

public final class ParserProgram implements Parser<SyProgram> {
	@Override
	public ParseResult<SyProgram> parse(List<String> tokens) {
		var C = new ParserClass();
		var expression = new ParserExpression();
		
		return C.star().andThen(classes -> 
		         expression.transform(body -> (SyProgram) new SyProgramLit(classes, body))).parse(tokens);
	}
}
