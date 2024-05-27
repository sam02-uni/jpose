package jpose.syntax;

import java.util.Objects;

public record SyExpressionAdd(SyExpression syExpressionFirst, SyExpression syExpressionSecond) implements SyExpression {
	public SyExpressionAdd {
		Objects.requireNonNull(syExpressionFirst);
		Objects.requireNonNull(syExpressionSecond);
	}

	@Override
	public SyExpressionAdd replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionFirstNew = this.syExpressionFirst.replace(variableName, syExpression);
		var syExpressionSecondNew = this.syExpressionSecond.replace(variableName, syExpression);
		return new SyExpressionAdd(syExpressionFirstNew, syExpressionSecondNew);
	}
}
