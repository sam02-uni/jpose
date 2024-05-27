package jpose.syntax;

import java.util.Objects;

public record SyTypeClass(String name) implements SyType {
	public SyTypeClass {
		Objects.requireNonNull(name);
	}
	
	@Override
	public boolean isReference() {
		return true;
	}

	@Override
	public SyValue ini() {
		return new SyValueReferenceConstant(new SyReferenceConstantNull());
	}
}
