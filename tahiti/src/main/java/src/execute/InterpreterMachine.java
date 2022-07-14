package src.execute;


import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ScriptElement;
import src.util.ScriptElementExecutorFactory;

import static src.execute.InterpreterState.*;


public final class InterpreterMachine extends FiniteStateMachine<InterpreterState, ScriptContext, ExecutionException> {

    private InterpreterMachine(TransitionMatrix<InterpreterState> matrix, ScriptElementExecutorFactory factory,
                               ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(PROGRAM, new ProgramTransducer(factory.create(ScriptElement.PROGRAM)));
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }

    public static InterpreterMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<InterpreterState> matrix =
                TransitionMatrix.<InterpreterState>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, PROGRAM)
                        .allowTransition(PROGRAM, FINISH)

                        .build();

        return new InterpreterMachine(matrix, factory, exceptionThrower);
    }
}
