package jpose.syntax;

import java.util.Objects;

public record SyExpressionGetfield(SyExpression syExpression, String fieldName) implements SyExpression {
	public SyExpressionGetfield {
		Objects.requireNonNull(syExpression);
		Objects.requireNonNull(fieldName);
	}

	@Override
	public SyExpressionGetfield replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionNew = this.syExpression.replace(variableName, syExpression);
		return new SyExpressionGetfield(syExpressionNew, this.fieldName);
	}
}
