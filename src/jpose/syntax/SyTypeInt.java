package jpose.syntax;

public record SyTypeInt() implements SyType {
	@Override
	public boolean isReference() {
		return false;
	}

	@Override
	public SyValue ini() {
		return new SyValuePrimitiveConstant(new SyPrimitiveConstantInt(new SyIntLit(0)));
	}
}
