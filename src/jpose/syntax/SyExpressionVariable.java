package jpose.syntax;

import java.util.Objects;

public record SyExpressionVariable(String variableName) implements SyExpression {
	public SyExpressionVariable {
		Objects.requireNonNull(variableName);
	}

	@Override
	public SyExpression replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		if (this.variableName.equals(variableName)) {
			return syExpression;
		} else {
			return this;
		}
	}
}
