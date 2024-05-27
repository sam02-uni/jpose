package jpose.syntax;

import java.util.Objects;

public record SyExpressionPutfield(SyExpression syExpressionFirst, String fieldName, SyExpression syExpressionSecond) implements SyExpression {
	public SyExpressionPutfield {
		Objects.requireNonNull(syExpressionFirst);
		Objects.requireNonNull(fieldName);
		Objects.requireNonNull(syExpressionSecond);
	}

	@Override
	public SyExpressionPutfield replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionFirstNew = this.syExpressionFirst.replace(variableName, syExpression);
		var syExpressionSecondNew = this.syExpressionSecond.replace(variableName, syExpression);
		return new SyExpressionPutfield(syExpressionFirstNew, this.fieldName, syExpressionSecondNew);
	}
}
