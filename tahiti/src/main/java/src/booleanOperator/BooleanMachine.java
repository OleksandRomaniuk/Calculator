package src.booleanOperator;

import fsm.ExceptionThrower;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ExecutorProgramElementTransducer;
import src.util.ScriptElement;
import src.util.ScriptElementExecutorFactory;

import static src.booleanOperator.BooleanStates.*;


public final class BooleanMachine extends FiniteStateMachine<BooleanStates, ScriptContext, ExecutionException> {

    private BooleanMachine(TransitionMatrix<BooleanStates> matrix, ExceptionThrower<ExecutionException> exceptionThrower,
                           ScriptElementExecutorFactory factory) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(VARIABLE, new BooleanOperatorTransducer());
        registerTransducer(RELATIONAL_EXPRESSION, new ExecutorProgramElementTransducer(ScriptElement.RELATIONAL_EXPRESSION, factory));
    }

    public static BooleanMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        var matrix =
                TransitionMatrix.<BooleanStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .withTemporaryState(RELATIONAL_EXPRESSION)
                        .withTemporaryState(VARIABLE)
                        .allowTransition(START, RELATIONAL_EXPRESSION, VARIABLE)
                        .allowTransition(VARIABLE, FINISH)
                        .allowTransition(RELATIONAL_EXPRESSION, FINISH)

                        .build();

        return new BooleanMachine(matrix, exceptionThrower, factory);
    }
}



