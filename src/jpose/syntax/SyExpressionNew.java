package jpose.syntax;

import java.util.Objects;

public record SyExpressionNew(String className) implements SyExpression {
	public SyExpressionNew {
		Objects.requireNonNull(className);
	}

	@Override
	public SyExpressionNew replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		return this;
	}
}
