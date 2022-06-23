package src.impl.fsm.function.name;

import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.impl.fsm.number.SymbolTransducer;

import static src.impl.fsm.function.name.IdentifierState.*;


/**
 *
 * IdentifierMachine is a realisation of {@link FiniteStateMachine}
 *     that implements a list of all possible transitions and actions when reading a name of function
 */

public final class IdentifierMachine extends FiniteStateMachine<IdentifierState, StringBuilder> {

    public static IdentifierMachine create() {

        TransitionMatrix<IdentifierState> matrix = TransitionMatrix.<IdentifierState>builder()

                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, CHARACTER)
                .allowTransition(CHARACTER, CHARACTER, FINISH)

                .build();
        return new IdentifierMachine(matrix);
    }

    private IdentifierMachine(TransitionMatrix<IdentifierState> matrix) {
        super(matrix);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(CHARACTER, new SymbolTransducer(Character::isLetter));
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}
