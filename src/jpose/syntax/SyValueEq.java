package jpose.syntax;

import java.util.Objects;

public record SyValueEq(SyValue syValueFirst, SyValue syValueSecond) implements SyValue {
	public SyValueEq {
		Objects.requireNonNull(syValueFirst);
		Objects.requireNonNull(syValueSecond);
	}
	
	@Override
	public boolean isReference() {
		return false;
	}
}
