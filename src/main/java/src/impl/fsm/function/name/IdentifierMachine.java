package src.impl.fsm.function.name;

import src.impl.fsm.FiniteStateMachine;
import src.impl.fsm.Transducer;
import src.impl.fsm.TransitionMatrix;
import src.impl.fsm.number.SymbolTransducer;

import static src.impl.fsm.function.name.IdentifierState.*;


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
