package src.identifier;


import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;

import java.util.function.Predicate;

/**
 * {@code IdentifierMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a name of function.
 */

public final class IdentifierMachine<E extends Exception> extends FiniteStateMachine<IdentifierStates, StringBuilder, E> {

    private IdentifierMachine(TransitionMatrix<IdentifierStates> matrix, ExceptionThrower<E> exceptionThrower, Predicate<Character> predicate) {
        super(matrix, exceptionThrower, false);

        registerTransducer(IdentifierStates.START, Transducer.illegalTransition());
        registerTransducer(IdentifierStates.LETTER, new SymbolTransducer<>(predicate));
        registerTransducer(IdentifierStates.FINISH, Transducer.autoTransition());
    }

    public static <E extends Exception> IdentifierMachine<E> create(ExceptionThrower<E> exceptionThrower, Predicate<Character> predicate) {

        TransitionMatrix<IdentifierStates> matrix = TransitionMatrix.<IdentifierStates>builder()

                .withStartState(IdentifierStates.START)
                .withFinishState(IdentifierStates.FINISH)
                .allowTransition(IdentifierStates.START, IdentifierStates.LETTER)
                .allowTransition(IdentifierStates.LETTER, IdentifierStates.LETTER, IdentifierStates.FINISH)

                .build();
        return new IdentifierMachine<>(matrix, exceptionThrower, predicate);
    }
}
