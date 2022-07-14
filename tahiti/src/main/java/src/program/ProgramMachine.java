package src.program;


import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ScriptElement;
import src.util.ScriptElementExecutorFactory;

import static src.program.ProgramStates.*;

/**
 * {@code ProgramMachine} is a realisation of {@link FiniteStateMachine} that used to
 * separate statement and launch machine for them.
 */

public final class ProgramMachine extends FiniteStateMachine<ProgramStates, ScriptContext, ExecutionException> {

    private ProgramMachine(TransitionMatrix<ProgramStates> matrix, ScriptElementExecutorFactory factory,
                           ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(STATEMENT, new StatementTransducer(factory.create(ScriptElement.STATEMENT)));
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(';'));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static ProgramMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<ProgramStates> matrix =
                TransitionMatrix.<ProgramStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, STATEMENT)
                        .allowTransition(STATEMENT, SEPARATOR, FINISH)
                        .allowTransition(SEPARATOR, STATEMENT, FINISH)

                        .build();

        return new ProgramMachine(matrix, factory, exceptionThrower);
    }
}
