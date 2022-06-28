package src.fsm.calculator;



import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.ShuntingYard;
import src.math.MathElement;
import src.math.MathElementResolverFactory;

import java.util.function.BiConsumer;

/**
 * CalculatorMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state
 *
 */

public final class CalculatorMachine extends FiniteStateMachine<CalculatorStates, ShuntingYard> {

    public static CalculatorMachine create(MathElementResolverFactory factory) {
        TransitionMatrix<CalculatorStates> matrix =
                TransitionMatrix.<CalculatorStates>builder()
                        .withStartState(CalculatorStates.START)
                        .withFinishState(CalculatorStates.FINISH)
                        .allowTransition(CalculatorStates.START, CalculatorStates.EXPRESSION)
                        .allowTransition(CalculatorStates.EXPRESSION, CalculatorStates.FINISH).build();

        return new CalculatorMachine(matrix, factory);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        registerTransducer(CalculatorStates.START, Transducer.illegalTransition());
        registerTransducer(CalculatorStates.EXPRESSION, new DetachedShuntingYardTransducer<>(MathElement.EXPRESSION, consumer, factory));
        registerTransducer(CalculatorStates.FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}