package src.calculator.impl.fsm.operand;

import src.fsm.FiniteStateMachine;
import src.fsm.TransitionMatrix;
import src.calculator.impl.ShuntingYardTransducer;
import src.calculator.impl.fsm.function.FunctionTransducer;
import src.calculator.impl.fsm.util.ShuntingYard;
import src.calculator.impl.math.MathElement;
import src.calculator.impl.math.MathElementResolverFactory;

import java.util.function.BiConsumer;

/**
 * NumberStateMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state when reading operand
 *
 */

public final class OperandMachine extends FiniteStateMachine<Object, ShuntingYard> {

    public static FiniteStateMachine<Object, ShuntingYard> create(MathElementResolverFactory factory) {

        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        return FiniteStateMachine.oneOfMachine(new NumberTransducer(factory.create(MathElement.NUMBER)),
                new ShuntingYardTransducer<>(factory.create(MathElement.BRACKETS), consumer),
                new FunctionTransducer(factory.create(MathElement.FUNCTION)));
    }

    private OperandMachine(TransitionMatrix<Object> matrix, MathElementResolverFactory factory) {
        super(matrix, true);
    }
}