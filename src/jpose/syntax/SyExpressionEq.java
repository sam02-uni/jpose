package jpose.syntax;

import java.util.Objects;

public record SyExpressionEq(SyExpression syExpressionFirst, SyExpression syExpressionSecond) implements SyExpression {
	public SyExpressionEq {
		Objects.requireNonNull(syExpressionFirst);
		Objects.requireNonNull(syExpressionSecond);
	}

	@Override
	public SyExpressionEq replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionFirstNew = this.syExpressionFirst.replace(variableName, syExpression);
		var syExpressionSecondNew = this.syExpressionSecond.replace(variableName, syExpression);
		return new SyExpressionEq(syExpressionFirstNew, syExpressionSecondNew);
	}
}
