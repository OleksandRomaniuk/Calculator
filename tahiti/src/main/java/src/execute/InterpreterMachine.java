package src.execute;


import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.runtime.ScriptContext;
import src.util.ScriptElement;
import src.util.ScriptElementExecutorFactory;

import static src.execute.InterpreterState.*;

public final class InterpreterMachine extends FiniteStateMachine<InterpreterState, ScriptContext> {

    private InterpreterMachine(TransitionMatrix<InterpreterState> matrix, ScriptElementExecutorFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(PROGRAM, new ProgramTransducer(factory.create(ScriptElement.PROGRAM)));
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }

    public static InterpreterMachine create(ScriptElementExecutorFactory factory) {
        TransitionMatrix<InterpreterState> matrix =
                TransitionMatrix.<InterpreterState>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, PROGRAM)
                        .allowTransition(PROGRAM, FINISH)

                        .build();

        return new InterpreterMachine(matrix, factory);
    }
}
