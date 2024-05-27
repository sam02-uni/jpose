package jpose.syntax;

import java.util.Objects;

public record SyPrimitiveConstantBool(SyBool syBool) implements SyPrimitiveConstant {
	public SyPrimitiveConstantBool {
		Objects.requireNonNull(syBool);
	}
}
