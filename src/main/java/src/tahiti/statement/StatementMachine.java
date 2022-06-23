package src.tahiti.statement;


import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.tahiti.VariableHolder;

public final class StatementMachine extends FiniteStateMachine<StatementStates, VariableHolder> {

    private StatementMachine(TransitionMatrix<StatementStates> matrix) {
        super(matrix, true);

        registerTransducer(StatementStates.START, Transducer.illegalTransition());
        registerTransducer(StatementStates.INIT_VAR, Transducer.illegalTransition());
        registerTransducer(StatementStates.PROCEDURE, Transducer.checkAndPassChar(';'));
        registerTransducer(StatementStates.FINISH, Transducer.autoTransition());
    }

    public static StatementMachine create(){
        TransitionMatrix<StatementStates> matrix = TransitionMatrix.<StatementStates>builder()
                .withStartState(StatementStates.START)
                .withFinishState(StatementStates.FINISH)
                .allowTransition(StatementStates.START, StatementStates.INIT_VAR)
                .allowTransition(StatementStates.INIT_VAR, StatementStates.FINISH)
                .allowTransition(StatementStates.START, StatementStates.PROCEDURE)
                .allowTransition(StatementStates.PROCEDURE, StatementStates.FINISH)

                .build();

        return new StatementMachine(matrix);
    }
}
