package fsm.identifier;


import fsm.ExceptionThrower;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;

/**
 * {@code IdentifierMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a name of function.
 */

public final class IdentifierMachine<E extends Exception> extends FiniteStateMachine<IdentifierStates, StringBuilder, E> {

    public static <E extends Exception> IdentifierMachine<E> create(ExceptionThrower<E> exceptionThrower) {

        var matrix = TransitionMatrix.<IdentifierStates>builder()

                .withStartState(IdentifierStates.START)
                .withFinishState(IdentifierStates.FINISH)
                .allowTransition(IdentifierStates.START, IdentifierStates.LETTER)
                .allowTransition(IdentifierStates.LETTER, IdentifierStates.LETTER, IdentifierStates.FINISH)

                .build();
        return new IdentifierMachine<>(matrix, exceptionThrower);
    }

    private IdentifierMachine(TransitionMatrix<IdentifierStates> matrix, ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower);

        registerTransducer(IdentifierStates.START, Transducer.illegalTransition());
        registerTransducer(IdentifierStates.LETTER, new SymbolTransducer<>(Character::isLetter));
        registerTransducer(IdentifierStates.FINISH, Transducer.autoTransition());
    }
}
