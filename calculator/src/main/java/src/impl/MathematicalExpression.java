package src.impl;

import com.google.common.base.Preconditions;

/**
 * {@code MathematicalExpression } is a tiny type which is used to store mathematical expression
 * which user want to resolve.
 */


public class MathematicalExpression {

    private final String expression;

    public MathematicalExpression(String expression) {
        Preconditions.checkNotNull(expression);
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }
}
