package src.expression;

import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ExecutorProgramElementTransducer;
import src.util.ProgramElement;
import src.util.ProgramFactory;

/**
 * {@code ScriptExpressionMachine} is an implementation of {@link FiniteStateMachine}.
 * {@code ScriptExpressionMachine} is a one of machine that intended to choose between logical and numeric expression.
 */

public final class ScriptExpressionMachine extends FiniteStateMachine<ScriptExpressionStates, ScriptContext, ExecutionException> {

    private ScriptExpressionMachine(TransitionMatrix<ScriptExpressionStates> matrix,
                                    ExceptionThrower<ExecutionException> exceptionThrower,
                                    ProgramFactory factory) {

        super(matrix, exceptionThrower, true);

        registerTransducer(ScriptExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ScriptExpressionStates.FINISH, Transducer.autoTransition());
        registerTransducer(ScriptExpressionStates.LOGICAL_EXPRESSION,
                new ExecutorProgramElementTransducer(ProgramElement.BOOLEAN_EXPRESSION, factory));
        registerTransducer(ScriptExpressionStates.NUMERIC_EXPRESSION,
                new ExecutorProgramElementTransducer(ProgramElement.NUMERIC_EXPRESSION, factory));
    }

    public static ScriptExpressionMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<ScriptExpressionStates> matrix =
                TransitionMatrix.<ScriptExpressionStates>builder()
                        .withStartState(ScriptExpressionStates.START)
                        .withFinishState(ScriptExpressionStates.FINISH)
                        .withTemporaryState(ScriptExpressionStates.LOGICAL_EXPRESSION)
                        .allowTransition(ScriptExpressionStates.START, ScriptExpressionStates.LOGICAL_EXPRESSION, ScriptExpressionStates.NUMERIC_EXPRESSION)
                        .allowTransition(ScriptExpressionStates.LOGICAL_EXPRESSION, ScriptExpressionStates.FINISH)
                        .allowTransition(ScriptExpressionStates.NUMERIC_EXPRESSION, ScriptExpressionStates.FINISH)

                        .build();

        return new ScriptExpressionMachine(matrix, exceptionThrower, factory);
    }
}
