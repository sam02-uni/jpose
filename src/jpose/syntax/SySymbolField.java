package jpose.syntax;

import java.util.List;
import java.util.Objects;

public record SySymbolField(int id, List<String> fieldNames) implements SySymbol {
	public SySymbolField {
		Objects.requireNonNull(fieldNames);
		for (String fieldName : fieldNames) {
			Objects.requireNonNull(fieldName);
		}
	}
}
