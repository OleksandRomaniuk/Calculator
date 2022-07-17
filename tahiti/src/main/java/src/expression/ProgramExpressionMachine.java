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


public final class ProgramExpressionMachine extends FiniteStateMachine<ProgramExpressionStates, ScriptContext, ExecutionException> {

    private ProgramExpressionMachine(TransitionMatrix<ProgramExpressionStates> matrix,
                                     ExceptionThrower<ExecutionException> exceptionThrower,
                                     ProgramFactory factory) {

        super(matrix, exceptionThrower, true);

        registerTransducer(ProgramExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ProgramExpressionStates.FINISH, Transducer.autoTransition());
        registerTransducer(ProgramExpressionStates.LOGICAL_EXPRESSION,
                new ExecutorProgramElementTransducer(ProgramElement.BOOLEAN_EXPRESSION, factory));
        registerTransducer(ProgramExpressionStates.NUMERIC_EXPRESSION,
                new ExecutorProgramElementTransducer(ProgramElement.NUMERIC_EXPRESSION, factory));
    }

    public static ProgramExpressionMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<ProgramExpressionStates> matrix =
                TransitionMatrix.<ProgramExpressionStates>builder()
                        .withStartState(ProgramExpressionStates.START)
                        .withFinishState(ProgramExpressionStates.FINISH)
                        .withTemporaryState(ProgramExpressionStates.LOGICAL_EXPRESSION)
                        .allowTransition(ProgramExpressionStates.START, ProgramExpressionStates.LOGICAL_EXPRESSION, ProgramExpressionStates.NUMERIC_EXPRESSION)
                        .allowTransition(ProgramExpressionStates.LOGICAL_EXPRESSION, ProgramExpressionStates.FINISH)
                        .allowTransition(ProgramExpressionStates.NUMERIC_EXPRESSION, ProgramExpressionStates.FINISH)

                        .build();

        return new ProgramExpressionMachine(matrix, exceptionThrower, factory);
    }
}
