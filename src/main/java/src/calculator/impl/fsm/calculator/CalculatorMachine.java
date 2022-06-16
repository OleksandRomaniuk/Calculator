package src.calculator.impl.fsm.calculator;

import src.calculator.impl.ShuntingYardTransducer;
import src.calculator.impl.fsm.FiniteStateMachine;
import src.calculator.impl.fsm.Transducer;
import src.calculator.impl.fsm.TransitionMatrix;
import src.calculator.impl.fsm.util.ShuntingYardStack;
import src.calculator.impl.math.MathElement;
import src.calculator.impl.math.MathElementResolverFactory;

/**
 * CalculatorMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state
 *
 */


public final class CalculatorMachine extends FiniteStateMachine<CalculatorState, ShuntingYardStack> {

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

        registerTransducer(CalculatorState.START, Transducer.illegalTransition());
        registerTransducer(CalculatorState.EXPRESSION, new ShuntingYardTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(CalculatorState.FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}