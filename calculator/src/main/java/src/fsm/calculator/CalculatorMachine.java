package src.fsm.calculator;


import fsm.ExceptionThrower;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.ResolvingException;
import src.fsm.ShuntingYard;
import src.math.MathElement;
import src.math.MathElementResolverFactory;
import fsm.type.Value;

import java.util.function.BiConsumer;


public final class CalculatorMachine extends FiniteStateMachine<CalculatorStates, ShuntingYard, ResolvingException> {

    public static CalculatorMachine create(MathElementResolverFactory factory,
                                           ExceptionThrower<ResolvingException> exceptionThrower) {
        var matrix =
                TransitionMatrix.<CalculatorStates>builder()
                        .withStartState(CalculatorStates.START)
                        .withFinishState(CalculatorStates.FINISH)
                        .allowTransition(CalculatorStates.START, CalculatorStates.EXPRESSION)
                        .allowTransition(CalculatorStates.EXPRESSION, CalculatorStates.FINISH).build();

        return new CalculatorMachine(matrix, factory, exceptionThrower);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorStates> matrix, MathElementResolverFactory factory, ExceptionThrower<ResolvingException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        var consumer = (BiConsumer<ShuntingYard, Value>) ShuntingYard::pushOperand;

        registerTransducer(CalculatorStates.START, Transducer.illegalTransition());
        registerTransducer(CalculatorStates.EXPRESSION, new DetachedShuntingYardTransducer<>(MathElement.EXPRESSION, consumer, factory));
        registerTransducer(CalculatorStates.FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}