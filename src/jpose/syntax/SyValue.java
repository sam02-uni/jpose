package jpose.syntax;

public sealed interface SyValue permits SyValueUnassumed, SyValuePrimitiveConstant, SyValueReferenceConstant, 
SyValueAdd, SyValueSub, SyValueLt, SyValueAnd, SyValueOr, SyValueNot, SyValueEq, SyValueSubtypeRel, 
SyValueFieldRel, SyValueIte {
	public default boolean isPrimitive() { return !isReference(); }
	public boolean isReference();
}
