package jpose.syntax;

import java.util.Objects;

public record SyValuePrimitiveConstant(SyPrimitiveConstant syPrimitiveConstant) implements SyValue {
	public SyValuePrimitiveConstant {
		Objects.requireNonNull(syPrimitiveConstant);
	}
	
	@Override
	public boolean isReference() {
		return false;
	}
}
