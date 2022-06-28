package src.fsm.function;



import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;

import java.util.function.BiConsumer;


/**
 * {@code FunctionMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a function.
 */

public final class FunctionMachine<O> extends FiniteStateMachine<FunctionStates, O> {

    private final FunctionFactory functionFactory = new FunctionFactory();

    public static <O> FunctionMachine<O> create(Transducer<O> expressionFunctionTransducer, BiConsumer<O, String> biConsumer){

        TransitionMatrix<FunctionStates> matrix = TransitionMatrix.<FunctionStates>builder()
                .withStartState(FunctionStates.START)
                .withFinishState(FunctionStates.FINISH)
                .withTemporaryState(FunctionStates.IDENTIFIER)
                .allowTransition(FunctionStates.START, FunctionStates.IDENTIFIER)
                .allowTransition(FunctionStates.IDENTIFIER, FunctionStates.OPENING_BRACKET)
                .allowTransition(FunctionStates.OPENING_BRACKET, FunctionStates.CLOSING_BRACKET, FunctionStates.EXPRESSION)
                .allowTransition(FunctionStates.EXPRESSION, FunctionStates.SEPARATOR, FunctionStates.CLOSING_BRACKET)
                .allowTransition(FunctionStates.SEPARATOR, FunctionStates.EXPRESSION)
                .allowTransition(FunctionStates.CLOSING_BRACKET, FunctionStates.FINISH)

                .build();

        return new FunctionMachine<>(matrix, expressionFunctionTransducer, biConsumer);
    }


    private FunctionMachine(TransitionMatrix<FunctionStates> matrix, Transducer<O> expressionFunctionTransducer,
                            BiConsumer<O, String> biConsumer) {
        super(matrix, true);

        registerTransducer(FunctionStates.START, Transducer.illegalTransition());
        registerTransducer(FunctionStates.FINISH, Transducer.autoTransition());
        registerTransducer(FunctionStates.OPENING_BRACKET, Transducer.checkAndPassChar('('));
        registerTransducer(FunctionStates.CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(FunctionStates.SEPARATOR, Transducer.checkAndPassChar(','));
        registerTransducer(FunctionStates.IDENTIFIER, new FunctionNameTransducer<>(biConsumer));
        registerTransducer(FunctionStates.EXPRESSION, expressionFunctionTransducer);
    }
}
