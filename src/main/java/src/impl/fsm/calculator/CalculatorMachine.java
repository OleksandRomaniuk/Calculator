package src.impl.fsm.calculator;

import src.impl.ShuntingYardTransducer;
import src.impl.fsm.FiniteStateMachine;
import src.impl.fsm.Transducer;
import src.impl.fsm.TransitionMatrix;
import src.impl.fsm.util.ShuntingYardStack;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;

import static src.impl.fsm.calculator.CalculatorState.*;

public final class CalculatorMachine extends FiniteStateMachine<CalculatorState, ShuntingYardStack> {

    public static CalculatorMachine create(MathElementResolverFactory factory) {
        TransitionMatrix<CalculatorState> matrix =
                TransitionMatrix.<CalculatorState>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, EXPRESSION)
                        .allowTransition(EXPRESSION, FINISH).build();

        return new CalculatorMachine(matrix, factory);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorState> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(EXPRESSION, new ShuntingYardTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}