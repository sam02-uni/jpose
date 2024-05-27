package jpose.syntax;

import java.util.Objects;

public record SyExpressionInvoke(SyExpression syExpressionFirst, String methodName, SyExpression syExpressionSecond) implements SyExpression {
	public SyExpressionInvoke {
		Objects.requireNonNull(syExpressionFirst);
		Objects.requireNonNull(methodName);
		Objects.requireNonNull(syExpressionSecond);
	}

	@Override
	public SyExpressionInvoke replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionFirstNew = this.syExpressionFirst.replace(variableName, syExpression);
		var syExpressionSecondNew = this.syExpressionSecond.replace(variableName, syExpression);
		return new SyExpressionInvoke(syExpressionFirstNew, this.methodName, syExpressionSecondNew);
	}
}
