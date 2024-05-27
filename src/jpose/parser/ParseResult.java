package jpose.parser;

import java.util.List;

public final class ParseResult<T> {
	private final List<String> tokensResidual;
	private OptionalError<T> parsed;
	
	ParseResult(List<String> tokensResidual, OptionalError<T> parsed) {
		this.tokensResidual = tokensResidual;
		this.parsed = parsed;
	}
	
	public List<String> tokensResidual() {
		return this.tokensResidual;
	}
	
	public OptionalError<T> parsed() {
		return this.parsed;
	}
}
