package jpose.syntax;

import java.util.Objects;

public record SyReferenceConstantLoc(SyLoc syLoc) implements SyReferenceConstant {
	public SyReferenceConstantLoc {
		Objects.requireNonNull(syLoc);
	}
}
