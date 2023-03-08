package src.programFeatures.booleanexpression;

import src.*;
import src.runtime.ProgramContext;
import src.util.ProgramElementTransducer;
import src.tahiti.*;

import static src.programFeatures.booleanexpression.BooleanOperandStates.*;


/**
 * Implementation of {@link FiniteStateMachine}
 * that used for parsing and executing boolean expressions.
 */

public final class BooleanOperandMachine extends FiniteStateMachine<BooleanOperandStates, ProgramContext, ExecutionException> {

    private BooleanOperandMachine(TransitionMatrix<BooleanOperandStates> matrix, ExceptionThrower<ExecutionException> exceptionThrower,
                                  ProgramFactory factory) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(BOOLEAN_VARIABLE, new BooleanVariableTransducer());
        registerTransducer(RELATIONAL_EXPRESSION, new ProgramElementTransducer(ProgramElement.RELATIONAL_EXPRESSION, factory));
    }

    public static BooleanOperandMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<BooleanOperandStates> matrix =
                TransitionMatrix.<BooleanOperandStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)

                        .withTemporaryState(RELATIONAL_EXPRESSION, BOOLEAN_VARIABLE)

                        .allowTransition(START, RELATIONAL_EXPRESSION, BOOLEAN_VARIABLE)
                        .allowTransition(BOOLEAN_VARIABLE, FINISH)
                        .allowTransition(RELATIONAL_EXPRESSION, FINISH)

                        .build();

        return new BooleanOperandMachine(matrix, exceptionThrower, factory);
    }
}
