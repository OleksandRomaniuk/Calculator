package src.program;


import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.runtime.ScriptContext;
import src.util.ScriptElement;
import src.util.ScriptElementExecutorFactory;

import static src.program.ProgramStates.*;

public final class ProgramMachine extends FiniteStateMachine<ProgramStates, ScriptContext> {

    private ProgramMachine(TransitionMatrix<ProgramStates> matrix, ScriptElementExecutorFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(STATEMENT, new StatementTransducer(factory.create(ScriptElement.STATEMENT)));
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(';'));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static ProgramMachine create(ScriptElementExecutorFactory factory) {
        TransitionMatrix<ProgramStates> matrix =
                TransitionMatrix.<ProgramStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, STATEMENT)
                        .allowTransition(STATEMENT, SEPARATOR, FINISH)
                        .allowTransition(SEPARATOR, STATEMENT, FINISH)

                        .build();

        return new ProgramMachine(matrix, factory);
    }
}
