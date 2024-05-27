package jpose.syntax;

import java.util.Objects;

public record SyPrimitiveConstantInt(SyInt syInt) implements SyPrimitiveConstant {
	public SyPrimitiveConstantInt {
		Objects.requireNonNull(syInt);
	}
}
