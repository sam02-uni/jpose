package jpose.syntax;

public record SyTypeBool() implements SyType {
	@Override
	public boolean isReference() {
		return false;
	}

	@Override
	public SyValue ini() {
		return new SyValuePrimitiveConstant(new SyPrimitiveConstantBool(new SyBoolFalse()));
	}
}
