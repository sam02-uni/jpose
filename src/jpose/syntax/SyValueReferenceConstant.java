package jpose.syntax;

import java.util.Objects;

public record SyValueReferenceConstant(SyReferenceConstant syReferenceConstant) implements SyValue {
	public SyValueReferenceConstant {
		Objects.requireNonNull(syReferenceConstant);
	}
	
	@Override
	public boolean isReference() {
		return true;
	}
}
