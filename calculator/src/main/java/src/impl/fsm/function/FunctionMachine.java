package src.impl.fsm.function;



import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;

import java.util.function.BiConsumer;

import static src.impl.fsm.function.FunctionStates.*;


/**
 * {@code FunctionMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a function.
 */

public final class FunctionMachine<O> extends FiniteStateMachine<FunctionStates, O> {

    private final FunctionFactory functionFactory = new FunctionFactory();

    public static <O> FunctionMachine<O> create(Transducer<O> expressionFunctionTransducer, BiConsumer<O, String> biConsumer){

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

        return new FunctionMachine<>(matrix, expressionFunctionTransducer, biConsumer);
    }


    private FunctionMachine(TransitionMatrix<FunctionStates> matrix, Transducer<O> expressionFunctionTransducer,
                            BiConsumer<O, String> biConsumer) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));
        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(','));
        registerTransducer(IDENTIFIER, new FunctionNameTransducer<>(biConsumer));
        registerTransducer(EXPRESSION, expressionFunctionTransducer);
    }
}
