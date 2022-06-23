package src.tahiti.program;


import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.tahiti.VariableHolder;

public final class ProgramMachine extends FiniteStateMachine<ProgramStates, VariableHolder> {

    private ProgramMachine(TransitionMatrix<ProgramStates> matrix) {
        super(matrix, true);

        registerTransducer(ProgramStates.START, Transducer.illegalTransition());
        registerTransducer(ProgramStates.STATEMENT, Transducer.illegalTransition());
        registerTransducer(ProgramStates.SEPARATOR, Transducer.checkAndPassChar(';'));
        registerTransducer(ProgramStates.FINISH, Transducer.autoTransition());
    }

    public static ProgramMachine create() {
        TransitionMatrix<ProgramStates> matrix =
                TransitionMatrix.<ProgramStates>builder()
                        .withStartState(ProgramStates.START)
                        .withFinishState(ProgramStates.FINISH)
                        .allowTransition(ProgramStates.START, ProgramStates.STATEMENT)
                        .allowTransition(ProgramStates.STATEMENT, ProgramStates.SEPARATOR, ProgramStates.FINISH)
                        .allowTransition(ProgramStates.SEPARATOR, ProgramStates.STATEMENT)

                        .build();

        return new ProgramMachine(matrix);
    }
}
