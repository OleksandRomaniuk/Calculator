package src.tahiti.execute;


import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.tahiti.VariableHolder;

public final class InterpreterMachine extends FiniteStateMachine<InterpreterState, VariableHolder> {

    private InterpreterMachine(TransitionMatrix<InterpreterState> matrix) {
        super(matrix, true);

        registerTransducer(InterpreterState.START, Transducer.illegalTransition());
        registerTransducer(InterpreterState.PROGRAM, new ProgramTransducer());
        registerTransducer(InterpreterState.FINISH, (inputChain, outputChain) -> !inputChain.canRead());
    }

    public static InterpreterMachine create() {
        TransitionMatrix<InterpreterState> matrix =
                TransitionMatrix.<InterpreterState>builder()
                        .withStartState(InterpreterState.START)
                        .withFinishState(InterpreterState.FINISH)
                        .allowTransition(InterpreterState.START, InterpreterState.PROGRAM)
                        .allowTransition(InterpreterState.PROGRAM, InterpreterState.FINISH)

                        .build();

        return new InterpreterMachine(matrix);
    }
}
