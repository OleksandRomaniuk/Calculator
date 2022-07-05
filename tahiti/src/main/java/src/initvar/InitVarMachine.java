package src.initvar;


import fsm.ExceptionThrower;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.fsm.function.FunctionNameTransducer;
import src.util.ExecutionException;
import src.util.ScriptElement;
import src.util.ScriptElementExecutorFactory;

import static src.initvar.InitVarStates.*;

/**
 * InitVarMachine is a realisation of FiniteStateMachine that used to variable initialisation.
 */

public final class InitVarMachine extends FiniteStateMachine<InitVarStates, InitVarContext, ExecutionException> {

    private InitVarMachine(TransitionMatrix<InitVarStates> matrix, ScriptElementExecutorFactory factory,
                           ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(ASSIGN, Transducer.checkAndPassChar('='));

        registerTransducer(NAME, new FunctionNameTransducer<>(InitVarContext::setVariableName,
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                }));

        registerTransducer(EXPRESSION, new VariableExpressionTransducer(factory.create(ScriptElement.EXPRESSION)));

        registerTransducer(FINISH, (inputChain, outputChain) -> {

            if(outputChain.isParseonly()){
                return true;
            }

            outputChain.getScriptContext().memory()
                    .setVariable(outputChain.getVariableName(), outputChain.getVariableValue());

            return true;
        });
    }

    public static InitVarMachine create(ScriptElementExecutorFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
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
