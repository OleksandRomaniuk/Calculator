package src.expression;


import src.*;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ScriptElement;
import src.util.ScriptElementExecutorFactory;

/**
 * {@code ScriptExpressionMachine} is an implementation of {@link FiniteStateMachine}.
 * {@code ScriptExpressionMachine} is a one of machine that intended to choose between logical and numeric expression.
 */

public final class ScriptExpressionMachine extends FiniteStateMachine<ScriptExpressionMachine.ScriptExpressionStates, ScriptContext, ExecutionException> {

    private ScriptExpressionMachine(TransitionMatrix<ScriptExpressionStates> matrix,
                                    ExceptionThrower<ExecutionException> exceptionThrower,
                                    ScriptElementExecutorFactory factory) {

        super(matrix, exceptionThrower, true);

        registerTransducer(ScriptExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ScriptExpressionStates.FINISH, Transducer.autoTransition());
        registerTransducer(ScriptExpressionStates.BOOLEAN_EXPRESSION,
                new ExecutorProgramElementTransducer(ScriptElement.BOOLEAN_EXPRESSION, factory));
        registerTransducer(ScriptExpressionStates.NUMERIC_EXPRESSION,
                new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, factory));
    }

    public static ScriptExpressionMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<ScriptExpressionStates> matrix =
                TransitionMatrix.<ScriptExpressionStates>builder()
                        .withStartState(ScriptExpressionStates.START)
                        .withFinishState(ScriptExpressionStates.FINISH)
                        .withTemporaryState(ScriptExpressionStates.BOOLEAN_EXPRESSION)
                        .allowTransition(ScriptExpressionStates.START, ScriptExpressionStates.BOOLEAN_EXPRESSION, ScriptExpressionStates.NUMERIC_EXPRESSION)
                        .allowTransition(ScriptExpressionStates.BOOLEAN_EXPRESSION, ScriptExpressionStates.FINISH)
                        .allowTransition(ScriptExpressionStates.NUMERIC_EXPRESSION, ScriptExpressionStates.FINISH)

                        .build();

        return new ScriptExpressionMachine(matrix, exceptionThrower, factory);
    }

    enum ScriptExpressionStates {
        START,
        FINISH,
        BOOLEAN_EXPRESSION,
        NUMERIC_EXPRESSION
    }
}
