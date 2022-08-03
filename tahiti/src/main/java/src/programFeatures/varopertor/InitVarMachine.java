package src.programFeatures.varopertor;

import src.*;
import src.calucator.fsm.function.NameTransducer;
import src.tahiti.*;

import static src.programFeatures.varopertor.InitVarStates.*;


/**
 * Realisation of {@link FiniteStateMachine} which was intended to parse variable initialization.
 */

public final class InitVarMachine extends FiniteStateMachine<InitVarStates, InitVarContext, ExecutionException> {

    private InitVarMachine(TransitionMatrix<InitVarStates> matrix, ProgramFactory factory,
                           ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(ASSIGN, Transducer.checkAndPassChar('='));

        registerTransducer(NAME, new NameTransducer<>(InitVarContext::setVariableName,
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                }).named("Variable name"));

        registerTransducer(EXPRESSION, new VariableExpressionTransducer<>(factory,
                InitVarContext::setVariableValue));

        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static InitVarMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<InitVarStates> matrix = TransitionMatrix.<InitVarStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)

                .withTemporaryState(NAME)

                .allowTransition(START, NAME)
                .allowTransition(NAME, ASSIGN)
                .allowTransition(ASSIGN, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH)

                .build();

        return new InitVarMachine(matrix, factory, exceptionThrower);
    }
}
