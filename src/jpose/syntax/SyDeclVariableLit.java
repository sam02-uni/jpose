package jpose.syntax;

import java.util.Objects;

public record SyDeclVariableLit(SyType syType, String variableName) implements SyDeclVariable {
	public SyDeclVariableLit {
		Objects.requireNonNull(syType);
		Objects.requireNonNull(variableName);
	}
}
