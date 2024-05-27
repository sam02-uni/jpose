package jpose.syntax;

import java.util.Objects;

public record SyExpressionIf(SyExpression syExpressionCond, SyExpression syExpressionThen, SyExpression syExpressionElse) implements SyExpression {
	public SyExpressionIf {
		Objects.requireNonNull(syExpressionCond);
		Objects.requireNonNull(syExpressionThen);
		Objects.requireNonNull(syExpressionElse);
	}

	@Override
	public SyExpressionIf replace(String variableName, SyExpression syExpression) {
		Objects.requireNonNull(variableName);
		Objects.requireNonNull(syExpression);
		
		var syExpressionCondNew = this.syExpressionCond.replace(variableName, syExpression);
		var syExpressionThenNew = this.syExpressionThen.replace(variableName, syExpression);
		var syExpressionElseNew = this.syExpressionElse.replace(variableName, syExpression);
		return new SyExpressionIf(syExpressionCondNew, syExpressionThenNew, syExpressionElseNew);
	}
}
