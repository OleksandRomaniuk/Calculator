package src.interpreter;

import src.*;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramFactory;


import static src.interpreter.InterpreterState.*;

/**
 * Realisation of {@link FiniteStateMachine} that used to launch {@link src.program.ProgramMachine}
 */

public final class InterpreterMachine extends FiniteStateMachine<InterpreterState, ProgramContext, ExecutionException> {

    private InterpreterMachine(TransitionMatrix<InterpreterState> matrix, ProgramFactory factory,
                               ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(PROGRAM, new ProgramTransducer(factory));

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
