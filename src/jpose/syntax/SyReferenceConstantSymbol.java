package jpose.syntax;

import java.util.Objects;

public record SyReferenceConstantSymbol(SySymbol sySymbol) implements SyReferenceConstant {
	public SyReferenceConstantSymbol {
		Objects.requireNonNull(sySymbol);
	}
}
