package jpose.semantics.interpreter;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import jpose.semantics.types.SemConfiguration;
import jpose.syntax.SyBoolTrue;
import jpose.syntax.SyDeclClassLit;
import jpose.syntax.SyDeclVariableLit;
import jpose.syntax.SyExpressionGetfield;
import jpose.syntax.SyExpressionIf;
import jpose.syntax.SyExpressionValue;
import jpose.syntax.SyIntLit;
import jpose.syntax.SyPrimitiveConstantBool;
import jpose.syntax.SyPrimitiveConstantInt;
import jpose.syntax.SyPrimitiveConstantSymbol;
import jpose.syntax.SyProgramLit;
import jpose.syntax.SyReferenceConstantSymbol;
import jpose.syntax.SySymbolExpression;
import jpose.syntax.SySymbolField;
import jpose.syntax.SyTypeClass;
import jpose.syntax.SyTypeInt;
import jpose.syntax.SyValuePrimitiveConstant;
import jpose.syntax.SyValueReferenceConstant;

class TestInterpreter {

	@Test
	void testStepRefinement1() {
		var C1 = new SyDeclClassLit("Object", "", List.of(), List.of());
		var e = new SyExpressionValue(new SyValuePrimitiveConstant(new SyPrimitiveConstantInt(new SyIntLit(0))));		
		var p = new SyProgramLit(List.of(C1), e);
		var J = new SemConfiguration(p);
		var I = new Interpreter();
		assertThrows(StuckException.class, () -> { I.stepRefinement(J); });
	}

	@Test
	void testStepRefinement2() {
		var C1 = new SyDeclClassLit("Object", "", List.of(), List.of());
		var e0 = new SyExpressionValue(new SyValuePrimitiveConstant(new SyPrimitiveConstantBool(new SyBoolTrue())));		
		var e1 = new SyExpressionValue(new SyValuePrimitiveConstant(new SyPrimitiveConstantInt(new SyIntLit(1))));		
		var e2 = new SyExpressionValue(new SyValuePrimitiveConstant(new SyPrimitiveConstantInt(new SyIntLit(0))));
		var e = new SyExpressionIf(e0, e1, e2);
		var p = new SyProgramLit(List.of(C1), e);
		var J = new SemConfiguration(p);
		var I = new Interpreter();
		assertThrows(StuckException.class, () -> { I.stepRefinement(J); });
	}

	@Test
	void testStepRefinement3() {
		var C1 = new SyDeclClassLit("Object", "", List.of(), List.of());
		var C2 = new SyDeclClassLit("C", "Object", List.of(new SyDeclVariableLit(new SyTypeInt(), "f")), List.of());
		var Y = new SyReferenceConstantSymbol(new SySymbolExpression(0));
		var e = new SyExpressionGetfield(new SyExpressionValue(new SyValueReferenceConstant(Y)), "f");
		var p = new SyProgramLit(List.of(C1, C2), e);
		var J = new SemConfiguration(p);
		var I = new Interpreter();
		I.stepRefinementStar(J);
		assertTrue(J.objectAt(Y).isPresent());
		assertTrue(J.objectAt(Y).get().get("f").isPresent());
		assertTrue(J.objectAt(Y).get().get("f").get().equals(new SyValuePrimitiveConstant(new SyPrimitiveConstantSymbol(new SySymbolField(0, List.of("f"))))));
	}

	@Test
	void testStepComputation1() {
		var C1 = new SyDeclClassLit("Object", "", List.of(), List.of());
		var C2 = new SyDeclClassLit("C", "Object", List.of(new SyDeclVariableLit(new SyTypeClass("C"), "f")), List.of());
		var Y = new SyReferenceConstantSymbol(new SySymbolExpression(0));
		var e1 = new SyExpressionGetfield(new SyExpressionValue(new SyValueReferenceConstant(Y)), "f");
		var e = new SyExpressionGetfield(e1, "f");
		var p = new SyProgramLit(List.of(C1, C2), e);
		var J = new SemConfiguration(p);
		var I = new Interpreter();
		I.stepRefinementStar(J);
		var J1s = I.stepComputation(J);
		assertTrue(J1s.size() == 1);
		var J1 = J1s.get(0);
		I.stepRefinementStar(J1);
		var J2s = I.stepComputation(J1);
		assertTrue(J2s.size() == 1);
		var J2 = J2s.get(0);
		assertTrue(J2.objectAt(Y).isPresent());
		assertTrue(J2.objectAt(Y).get().get("f").isPresent());
		var Y_f = new SyReferenceConstantSymbol(new SySymbolField(0, List.of("f")));
		assertTrue(J2.objectAt(Y).get().get("f").get().equals(new SyValueReferenceConstant(Y_f)));
	}
}
