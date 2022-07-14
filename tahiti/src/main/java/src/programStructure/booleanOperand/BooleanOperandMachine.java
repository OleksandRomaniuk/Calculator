package src.programStructure.booleanOperand;

import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ExecutorProgramElementTransducer;
import src.util.ProgramElement;
import src.util.ProgramFactory;

import static src.programStructure.booleanOperand.BooleanOperandStates.*;


public final class BooleanOperandMachine extends FiniteStateMachine<BooleanOperandStates, ScriptContext, ExecutionException> {

    private BooleanOperandMachine(TransitionMatrix<BooleanOperandStates> matrix, ExceptionThrower<ExecutionException> exceptionThrower,
                                  ProgramFactory factory) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(FINISH, Transducer.autoTransition());
        registerTransducer(BOOLEAN_VARIABLE, new BooleanTransducer());
        registerTransducer(RELATIONAL_EXPRESSION, new ExecutorProgramElementTransducer(ProgramElement.RELATIONAL_EXPRESSION, factory));
    }

    public static BooleanOperandMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<BooleanOperandStates> matrix =
                TransitionMatrix.<BooleanOperandStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .withTemporaryState(RELATIONAL_EXPRESSION)
                        .withTemporaryState(BOOLEAN_VARIABLE)
                        .allowTransition(START, RELATIONAL_EXPRESSION, BOOLEAN_VARIABLE)
                        .allowTransition(BOOLEAN_VARIABLE, FINISH)
                        .allowTransition(RELATIONAL_EXPRESSION, FINISH)

                        .build();

        return new BooleanOperandMachine(matrix, exceptionThrower, factory);
    }
}
