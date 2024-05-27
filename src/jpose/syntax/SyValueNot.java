package jpose.syntax;

import java.util.Objects;

public record SyValueNot(SyValue syValue) implements SyValue {
	public SyValueNot {
		Objects.requireNonNull(syValue);
	}
	
	@Override
	public boolean isReference() {
		return false;
	}
}
