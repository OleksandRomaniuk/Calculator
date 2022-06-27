package src.impl.fsm.operand;



import fsm.FiniteStateMachine;
import fsm.TransitionMatrix;
import src.impl.fsm.calculator.DetachedShuntingYardTransducer;
import src.impl.fsm.util.ShuntingYard;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;

import java.util.function.BiConsumer;

/**
 * {@code OperandMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing an operand. How an operand can act a number, an expression in brackets or function.
 */

public final class OperandMachine extends FiniteStateMachine<Object, ShuntingYard> {

    public static FiniteStateMachine<Object, ShuntingYard> create(MathElementResolverFactory factory) {

        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        return FiniteStateMachine.oneOfMachine(new DetachedShuntingYardTransducer<>(MathElement.NUMBER, ShuntingYard::pushOperand, factory),
                new DetachedShuntingYardTransducer<>(MathElement.BRACKETS, ShuntingYard::pushOperand, factory),
                new DetachedShuntingYardTransducer<>(MathElement.FUNCTION, ShuntingYard::pushOperand, factory));
    }

    private OperandMachine(TransitionMatrix<Object> matrix) {
        super(matrix, true);
    }
}

