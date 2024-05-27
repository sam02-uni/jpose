package jpose.syntax;

import java.util.Objects;

public record SyExpressionLt(SyExpression syExpressionFirst, SyExpression syExpressionSecond) implements SyExpression {
	public SyExpressionLt {
		Objects.requireNonNull(syExpressionFirst);
		Objects.requireNonNull(syExpressionSecond);
	}

	@Override
	public SyExpressionLt replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionFirstNew = this.syExpressionFirst.replace(variableName, syExpression);
		var syExpressionSecondNew = this.syExpressionSecond.replace(variableName, syExpression);
		return new SyExpressionLt(syExpressionFirstNew, syExpressionSecondNew);
	}
}
