package src.execute;

import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramFactory;

import static src.execute.InterpreterState.*;

/**
 * {@code InterpreterMachine} is a realisation of {@link FiniteStateMachine} that used to
 * launch ProgramMachine
 */

public final class InterpreterMachine extends FiniteStateMachine<InterpreterState, ScriptContext, ExecutionException> {

    private InterpreterMachine(TransitionMatrix<InterpreterState> matrix, ProgramFactory factory,
                               ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(PROGRAM, new ProgramTransducer(factory.create(ProgramElement.PROGRAM)));
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }

    public static InterpreterMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
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
