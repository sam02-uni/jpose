package jpose.syntax;

import java.util.Objects;

public record SyPrimitiveConstantSymbol(SySymbol sySymbol) implements SyPrimitiveConstant {
	public SyPrimitiveConstantSymbol {
		Objects.requireNonNull(sySymbol);
	}
}
