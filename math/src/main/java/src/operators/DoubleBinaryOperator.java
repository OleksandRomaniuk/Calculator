package src.operators;


import src.operators.AbstractBinaryOperator;
import src.type.DoubleValue;
import src.type.DoubleValueVisitor;
import src.type.Value;

import java.util.function.BiFunction;
/**
 * Implementation of {@link AbstractBinaryOperator} for operations with double values
 */
public class DoubleBinaryOperator extends AbstractBinaryOperator {

    private final BiFunction<Double, Double, Double> origin;

    DoubleBinaryOperator(Priority priority, BiFunction<Double, Double, Double> origin) {
        super(priority);
        this.origin = origin;
    }

    @Override
    public Value apply(Value left, Value right) {

        double leftOperand = DoubleValueVisitor.read(left);

        double rightOperand = DoubleValueVisitor.read(right);

        Double result = origin.apply(leftOperand, rightOperand);

        return new DoubleValue(result);
    }
}
