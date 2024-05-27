package jpose.syntax;

import java.util.Objects;

public record SyValueSubtypeRel(SyValue syValue, SyType syType) implements SyValue {
	public SyValueSubtypeRel {
		Objects.requireNonNull(syValue);
		Objects.requireNonNull(syType);
	}
	
	@Override
	public boolean isReference() {
		return false;
	}
}
