package jpose.syntax;

import java.util.Objects;

public record SyValueAnd(SyValue syValueFirst, SyValue syValueSecond) implements SyValue {
	public SyValueAnd {
		Objects.requireNonNull(syValueFirst);
		Objects.requireNonNull(syValueSecond);
	}
	
	@Override
	public boolean isReference() {
		return false;
	}
}
