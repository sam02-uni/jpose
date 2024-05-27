package jpose.syntax;

public sealed interface SyType permits SyTypeBool, SyTypeInt, SyTypeClass {
	public default boolean isPrimitive() { return !isReference(); }
	public boolean isReference();
	public SyValue ini();
}
