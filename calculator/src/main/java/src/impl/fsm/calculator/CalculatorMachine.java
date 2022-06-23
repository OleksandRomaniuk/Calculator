package src.impl.fsm.calculator;

import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.impl.ShuntingYardTransducer;
import src.impl.fsm.util.ShuntingYard;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;

import java.util.function.BiConsumer;

/**
 * CalculatorMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state
 *
 */


public final class CalculatorMachine extends FiniteStateMachine<CalculatorState, ShuntingYard> {

    public static CalculatorMachine create(MathElementResolverFactory factory) {
        TransitionMatrix<CalculatorState> matrix =
                TransitionMatrix.<CalculatorState>builder()
                        .withStartState(CalculatorState.START)
                        .withFinishState(CalculatorState.FINISH)
                        .allowTransition(CalculatorState.START, CalculatorState.EXPRESSION)
                        .allowTransition(CalculatorState.EXPRESSION, CalculatorState.FINISH).build();

        return new CalculatorMachine(matrix, factory);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorState> matrix, MathElementResolverFactory factory) {
        super(matrix, true);
        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        registerTransducer(CalculatorState.START, Transducer.illegalTransition());
        registerTransducer(CalculatorState.EXPRESSION, new ShuntingYardTransducer(factory.create(MathElement.EXPRESSION),consumer));
        registerTransducer(CalculatorState.FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}