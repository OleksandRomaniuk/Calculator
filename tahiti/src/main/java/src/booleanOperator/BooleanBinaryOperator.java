package src.booleanOperator;


import fsm.type.BooleanValue;
import fsm.type.BooleanValueVisitor;
import fsm.type.Value;
import src.AbstractBinaryOperator;

import java.util.function.BinaryOperator;

/**
 * {@BooleanBinaryOperator} is an extension {@AbstractBinaryOperator for for operations on logical operators
 */

public class BooleanBinaryOperator extends AbstractBinaryOperator {

    private final BinaryOperator<Boolean> origin;

    public BooleanBinaryOperator(Priority priority, BinaryOperator<Boolean> origin) {
        super(priority);
        this.origin = origin;
    }

    @Override
    public Value apply(Value left, Value right) {

        boolean leftOperand = BooleanValueVisitor.read(left);

        boolean rightOperand = BooleanValueVisitor.read(right);

        Boolean result = origin.apply(leftOperand, rightOperand);

        return new BooleanValue(result);
    }
}
