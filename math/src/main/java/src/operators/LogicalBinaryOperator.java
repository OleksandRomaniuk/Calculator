package src.operators;



import src.operators.AbstractBinaryOperator;
import src.type.BooleanValue;
import src.type.BooleanValueVisitor;
import src.type.Value;

import java.util.function.BiFunction;

/**
 * {@code LogicalBinaryOperator} is an implementation of {@link AbstractBinaryOperator}
 * that used to define functionality of binary operators for boolean data type.
 */

class LogicalBinaryOperator extends AbstractBinaryOperator {

    private final BiFunction<Boolean, Boolean, Boolean> origin;

    LogicalBinaryOperator(Priority priority, BiFunction<Boolean, Boolean, Boolean> origin) {
        super(priority);
        this.origin = origin;
    }

    @Override
    public Value apply(Value left, Value right) {
        Boolean leftOperand = BooleanValueVisitor.read(left);

        Boolean rightOperand = BooleanValueVisitor.read(right);

        Boolean result = origin.apply(leftOperand, rightOperand);

        return new BooleanValue(result);
    }
}
