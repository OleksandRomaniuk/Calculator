package src.calucator.fsm.calculator;


import src.*;
import src.calucator.ResolvingException;
import src.calucator.fsm.function.ShuntingYard;
import src.calucator.math.MathElement;
import src.calucator.math.MathElementResolverFactory;
import src.type.Value;
import src.calucator.DetachedShuntingYardTransducer;

import java.util.function.BiConsumer;


public final class CalculatorMachine extends FiniteStateMachine<CalculatorStates, ShuntingYard, ResolvingException> {

    public static CalculatorMachine create(MathElementResolverFactory factory,
                                           ExceptionThrower<ResolvingException> exceptionThrower) {
        TransitionMatrix<CalculatorStates> matrix =
                TransitionMatrix.<CalculatorStates>builder()
                        .withStartState(CalculatorStates.START)
                        .withFinishState(CalculatorStates.FINISH)
                        .allowTransition(CalculatorStates.START, CalculatorStates.EXPRESSION)
                        .allowTransition(CalculatorStates.EXPRESSION, CalculatorStates.FINISH).build();

        return new CalculatorMachine(matrix, factory, exceptionThrower);
    }

    private CalculatorMachine(TransitionMatrix<CalculatorStates> matrix, MathElementResolverFactory factory, ExceptionThrower<ResolvingException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        BiConsumer<ShuntingYard, Value> consumer = ShuntingYard::pushOperand;

        registerTransducer(CalculatorStates.START, Transducer.illegalTransition());
        registerTransducer(CalculatorStates.EXPRESSION, new DetachedShuntingYardTransducer<>(MathElement.EXPRESSION, consumer, factory));
        registerTransducer(CalculatorStates.FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }
}