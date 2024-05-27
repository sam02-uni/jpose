package jpose.syntax;

import java.util.Objects;

public record SyValueAdd(SyValue syValueFirst, SyValue syValueSecond) implements SyValue {
	public SyValueAdd {
		Objects.requireNonNull(syValueFirst);
		Objects.requireNonNull(syValueSecond);
	}
	
	@Override
	public boolean isReference() {
		return false;
	}
}
