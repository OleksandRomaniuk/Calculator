package src;


import fsm.type.BooleanValue;
import fsm.type.DoubleValueVisitor;
import fsm.type.Value;

import java.util.function.BiFunction;

public class RelationalBinaryOperator extends AbstractBinaryOperator{

    private final BiFunction<Double, Double, Boolean> origin;

    RelationalBinaryOperator(Priority priority, BiFunction<Double, Double, Boolean> origin) {
        super(priority);
        this.origin = origin;
    }

    @Override
    public Value apply(Value left, Value right) {
        double leftOperand = DoubleValueVisitor.read(left);

        double rightOperand = DoubleValueVisitor.read(right);

        Boolean result = origin.apply(leftOperand, rightOperand);

        return new BooleanValue(result);
    }
}
