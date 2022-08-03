package src.operators;


import src.operators.AbstractBinaryOperator;
import src.type.StringValue;
import src.type.StringValueVisitor;
import src.type.Value;

import java.util.function.BiFunction;

/**
 * Implementation of {@link AbstractBinaryOperator} for operations between strings.
 */

class StringBinaryOperator extends AbstractBinaryOperator {

    private final BiFunction<String, String, String> origin;

    StringBinaryOperator(Priority priority, BiFunction<String, String, String> origin) {
        super(priority);
        this.origin = origin;
    }

    @Override
    public Value apply(Value left, Value right) {
        String leftOperand = StringValueVisitor.read(left);

        String rightOperand = StringValueVisitor.read(right);

        String result = origin.apply(leftOperand, rightOperand);

        return new StringValue(result);
    }
}
