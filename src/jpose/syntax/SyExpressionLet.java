package jpose.syntax;

import java.util.Objects;

public record SyExpressionLet(String variableName, SyExpression syExpressionFirst, SyExpression syExpressionSecond) implements SyExpression {
	public SyExpressionLet {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpressionFirst);
		Objects.requireNonNull(syExpressionSecond);
	}

	@Override
	public SyExpressionLet replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		if (this.variableName.equals(variableName)) {
			return this;
		} else {			
			var syExpressionFirstNew = this.syExpressionFirst.replace(variableName, syExpression);
			var syExpressionSecondNew = this.syExpressionSecond.replace(variableName, syExpression);
			return new SyExpressionLet(this.variableName, syExpressionFirstNew, syExpressionSecondNew);
		}
	}
}
