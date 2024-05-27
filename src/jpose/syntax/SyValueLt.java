package jpose.syntax;

import java.util.Objects;

public record SyValueLt(SyValue syValueFirst, SyValue syValueSecond) implements SyValue {
	public SyValueLt {
		Objects.requireNonNull(syValueFirst);
		Objects.requireNonNull(syValueSecond);
	}
	
	@Override
	public boolean isReference() {
		return false;
	}
}
