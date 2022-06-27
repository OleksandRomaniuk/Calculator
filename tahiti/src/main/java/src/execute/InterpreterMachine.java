package src.execute;


import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.ProgramMemory;
import src.impl.math.MathElementResolverFactory;

import static src.execute.InterpreterState.*;

public final class InterpreterMachine extends FiniteStateMachine<InterpreterState, ProgramMemory> {

    private InterpreterMachine(TransitionMatrix<InterpreterState> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(PROGRAM, new ProgramTransducer(factory));
        registerTransducer(FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }

    public static InterpreterMachine create(MathElementResolverFactory factory) {
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
