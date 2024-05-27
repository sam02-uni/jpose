package jpose.syntax;

import java.util.Objects;

public record SyExpressionNot(SyExpression syExpression) implements SyExpression {
	public SyExpressionNot {
		Objects.requireNonNull(syExpression);
	}

	@Override
	public SyExpressionNot replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionNew = this.syExpression.replace(variableName, syExpression);
		return new SyExpressionNot(syExpressionNew);
	}
}
