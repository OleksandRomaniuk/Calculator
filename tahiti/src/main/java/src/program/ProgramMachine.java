package src.program;


import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.ProgramMemory;
import src.impl.math.MathElementResolverFactory;

import static src.program.ProgramStates.*;

public final class ProgramMachine extends FiniteStateMachine<ProgramStates, ProgramMemory> {

    private ProgramMachine(TransitionMatrix<ProgramStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(STATEMENT, new StatementTransducer(factory));
        registerTransducer(SEPARATOR, Transducer.checkAndPassChar(';'));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static ProgramMachine create(MathElementResolverFactory factory) {
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
