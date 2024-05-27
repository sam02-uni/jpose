package jpose.syntax;

import java.util.Objects;

public record SyExpressionInstanceof(SyExpression syExpression, String className) implements SyExpression {
	public SyExpressionInstanceof {
		Objects.requireNonNull(syExpression);
		Objects.requireNonNull(className);
	}

	@Override
	public SyExpressionInstanceof replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionNew = this.syExpression.replace(variableName, syExpression);
		return new SyExpressionInstanceof(syExpressionNew, this.className);
	}
}
