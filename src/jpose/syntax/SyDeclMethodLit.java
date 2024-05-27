package jpose.syntax;

import java.util.Objects;

public record SyDeclMethodLit(SyType syType, String methodName, SyDeclVariable syDeclVariable, SyExpression syExpression) implements SyDeclMethod {
	public SyDeclMethodLit {
		Objects.requireNonNull(syType);
		Objects.requireNonNull(methodName);
		Objects.requireNonNull(syDeclVariable);
		Objects.requireNonNull(syExpression);
	}
}
