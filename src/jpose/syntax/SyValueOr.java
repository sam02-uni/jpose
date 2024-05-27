package jpose.syntax;

import java.util.Objects;

public record SyValueOr(SyValue syValueFirst, SyValue syValueSecond) implements SyValue {
	public SyValueOr {
		Objects.requireNonNull(syValueFirst);
		Objects.requireNonNull(syValueSecond);
	}
	
	@Override
	public boolean isReference() {
		return false;
	}
}
