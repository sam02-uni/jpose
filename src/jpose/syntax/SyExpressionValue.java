package jpose.syntax;

import java.util.Objects;

public record SyExpressionValue(SyValue syValue) implements SyExpression {
	public SyExpressionValue {
		Objects.requireNonNull(syValue);
	}

	@Override
	public SyExpressionValue replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		return this;
	}
}
