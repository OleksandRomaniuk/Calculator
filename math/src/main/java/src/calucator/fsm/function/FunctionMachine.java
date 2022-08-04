package src.calucator.fsm.function;



import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;

import java.util.function.BiConsumer;

import static src.calucator.fsm.function.FunctionStates.*;


/**
 * Realisation of {@link FiniteStateMachine} for parsing a function.
 */

public final class FunctionMachine<O, E extends Exception> extends FiniteStateMachine<FunctionStates, O, E> {

    private FunctionMachine(TransitionMatrix<FunctionStates> matrix, Transducer<O, E> expressionFunctionTransducer,
                            Transducer<O, E> openingSignTransducer, Transducer<O, E> closingSignTransducer,
                            BiConsumer<O, String> identifierBiConsumer, ExceptionThrower<E> exceptionThrower) {

        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.autoTransition());

        registerTransducer(OPENING_BRACKET, openingSignTransducer);

        registerTransducer(CLOSING_BRACKET, closingSignTransducer);

        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(','));

        registerTransducer(IDENTIFIER, new NameTransducer<>(identifierBiConsumer, exceptionThrower).named("Function name"));

        registerTransducer(EXPRESSION, expressionFunctionTransducer);
    }

    public static <O, E extends Exception> FunctionMachine<O, E> create(Transducer<O, E> openingSignTransducer,
                                                                        BiConsumer<O, String> identifierBiConsumer,
                                                                        Transducer<O, E> expressionFunctionTransducer,
                                                                        Transducer<O, E> closingSignTransducer,
                                                                        ExceptionThrower<E> exceptionThrower) {

        TransitionMatrix<FunctionStates> matrix = TransitionMatrix.<FunctionStates>builder()
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

        return new FunctionMachine<>(matrix, expressionFunctionTransducer, openingSignTransducer, closingSignTransducer,
                identifierBiConsumer, exceptionThrower);
    }
}
