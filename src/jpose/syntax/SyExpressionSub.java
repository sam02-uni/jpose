package jpose.syntax;

import java.util.Objects;

public record SyExpressionSub(SyExpression syExpressionFirst, SyExpression syExpressionSecond) implements SyExpression {
	public SyExpressionSub {
		Objects.requireNonNull(syExpressionFirst);
		Objects.requireNonNull(syExpressionSecond);
	}

	@Override
	public SyExpressionSub replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionFirstNew = this.syExpressionFirst.replace(variableName, syExpression);
		var syExpressionSecondNew = this.syExpressionSecond.replace(variableName, syExpression);
		return new SyExpressionSub(syExpressionFirstNew, syExpressionSecondNew);
	}
}
