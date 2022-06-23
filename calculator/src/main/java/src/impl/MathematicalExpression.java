package src.impl;

import com.google.common.base.Preconditions;

/**
 * Class for generalizations input
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
