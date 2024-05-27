package jpose.syntax;

public record SyValueUnassumed() implements SyValue {
	@Override
	public boolean isReference() {
		return false; //imprecise, not meant to be used
	}
}
