package src.program;

import src.*;
import src.runtime.ProgramContext;
import src.tahiti.*;

import static src.program.ProgramStates.*;


/**
 * Machine for separating statements and launching machine for them.
 */

public final class ProgramMachine extends FiniteStateMachine<ProgramStates, ProgramContext, ExecutionException> {

    private ProgramMachine(TransitionMatrix<ProgramStates> matrix, ProgramFactory factory,
                           ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(STATEMENT, new StatementTransducer(factory)
                .and((inputChain, outputChain) -> {

                    outputChain.memory().restore();

                    return true;
                }));

        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(';'));

        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static ProgramMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
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
