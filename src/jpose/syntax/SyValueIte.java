package jpose.syntax;

import java.util.Objects;

public record SyValueIte(SyValue syValueCond, SyValue syValueThen, SyValue syValueElse) implements SyValue {
	public SyValueIte {
		Objects.requireNonNull(syValueCond);
		Objects.requireNonNull(syValueThen);
		Objects.requireNonNull(syValueElse);
	}
	
	@Override
	public boolean isReference() {
		return this.syValueThen.isReference();
	}
}
