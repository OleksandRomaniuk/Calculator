package src.fsm.function;



import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;

import java.util.function.BiConsumer;

import static src.fsm.function.FunctionStates.*;


/**
 * {@code FunctionMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a function.
 */

public final class FunctionMachine<O, E extends Exception> extends FiniteStateMachine<FunctionStates, O, E> {

    public static <O, E extends Exception> FunctionMachine<O, E> create(Transducer<O, E> expressionFunctionTransducer,
                                                                        BiConsumer<O, String> biConsumer,
                                                                        ExceptionThrower<E> exceptionThrower){

        var matrix = TransitionMatrix.<FunctionStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .withTemporaryState(IDENTIFIER)
                .allowTransition(START, IDENTIFIER)
                .allowTransition(IDENTIFIER, OPENING_BRACKET)
                .allowTransition(OPENING_BRACKET, CLOSING_BRACKET, EXPRESSION)
                .allowTransition(EXPRESSION, SEPARATOR, CLOSING_BRACKET)
                .allowTransition(SEPARATOR, EXPRESSION)
                .allowTransition(CLOSING_BRACKET, FINISH)

                .build();

        return new FunctionMachine<>(matrix, expressionFunctionTransducer, biConsumer, exceptionThrower);
    }


    private FunctionMachine(TransitionMatrix<FunctionStates> matrix, Transducer<O, E> expressionFunctionTransducer,
                            BiConsumer<O, String> biConsumer, ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));
        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(','));
        registerTransducer(IDENTIFIER, new FunctionNameTransducer<>(biConsumer, exceptionThrower));
        registerTransducer(EXPRESSION, expressionFunctionTransducer);
    }
}
