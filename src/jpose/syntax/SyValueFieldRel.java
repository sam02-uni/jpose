package jpose.syntax;

import java.util.Objects;

public record SyValueFieldRel(SySymbol sySymbolFirst, String fieldName, SySymbol sySymbolSecond) implements SyValue {
	public SyValueFieldRel {
		Objects.requireNonNull(sySymbolFirst);
		Objects.requireNonNull(fieldName);
		Objects.requireNonNull(sySymbolSecond);
	}
	
	@Override
	public boolean isReference() {
		return false;
	}
}
