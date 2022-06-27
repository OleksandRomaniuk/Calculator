package src.statement;


import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.ProgramMemory;
import src.impl.math.MathElementResolverFactory;

import static src.statement.StatementStates.*;

public final class StatementMachine extends FiniteStateMachine<StatementStates, ProgramMemory> {

    private StatementMachine(TransitionMatrix<StatementStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(INIT_VAR, new InitVarTransducer(factory));
        registerTransducer(PROCEDURE, new ProcedureTransducer(factory));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static StatementMachine create(MathElementResolverFactory factory){
        TransitionMatrix<StatementStates> matrix = TransitionMatrix.<StatementStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, INIT_VAR, PROCEDURE)
                .allowTransition(INIT_VAR, FINISH)
                .allowTransition(PROCEDURE, FINISH)

                .build();

        return new StatementMachine(matrix, factory);
    }
}
