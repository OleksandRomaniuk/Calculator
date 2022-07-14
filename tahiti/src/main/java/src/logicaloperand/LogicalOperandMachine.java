package src.logicaloperand;

import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ExecutorProgramElementTransducer;
import src.util.ScriptElement;
import src.util.ScriptElementExecutorFactory;

import static src.logicaloperand.LogicalOperandStates.*;


public final class LogicalOperandMachine extends FiniteStateMachine<LogicalOperandStates, ScriptContext, ExecutionException> {

    private LogicalOperandMachine(TransitionMatrix<LogicalOperandStates> matrix, ExceptionThrower<ExecutionException> exceptionThrower,
                                  ScriptElementExecutorFactory factory) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(READ_BOOLEAN_VARIABLE, new ReadBooleanVariableTransducer());
        registerTransducer(RELATIONAL_EXPRESSION, new ExecutorProgramElementTransducer(ScriptElement.RELATIONAL_EXPRESSION, factory));
    }

    public static LogicalOperandMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<LogicalOperandStates> matrix =
                TransitionMatrix.<LogicalOperandStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .withTemporaryState(RELATIONAL_EXPRESSION)
                        .withTemporaryState(READ_BOOLEAN_VARIABLE)
                        .allowTransition(START, RELATIONAL_EXPRESSION, READ_BOOLEAN_VARIABLE)
                        .allowTransition(READ_BOOLEAN_VARIABLE, FINISH)
                        .allowTransition(RELATIONAL_EXPRESSION, FINISH)

                        .build();

        return new LogicalOperandMachine(matrix, exceptionThrower, factory);
    }
}
