package src.programStructure.ternary;


import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramFactory;

import static src.programStructure.ternary.TernaryStates.*;

/**
 * Realization of FiniteStateMachine for parsing ternary operator
 */

public final class TernaryOperatorMachine extends FiniteStateMachine<TernaryStates, TernaryOperatorContext, ExecutionException> {

    private TernaryOperatorMachine(TransitionMatrix<TernaryStates> matrix, ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);


        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.autoTransition());

        registerTransducer(BOOLEAN_EXPRESSION, new BooleanExpressionTransducer(factory.create(ProgramElement.BOOLEAN_EXPRESSION)));

        registerTransducer(EXPRESSION, new TernaryOperatorTransducer(factory.create(ProgramElement.EXPRESSION)));

        registerTransducer(MARK, Transducer.<TernaryOperatorContext, ExecutionException>checkAndPassChar('?').and(
                (inputChain, outputChain) -> {
                    if (!outputChain.ternaryOperatorCondition()) {
                        outputChain.getScriptContext().setParsingPermission(true);

                    }
                    return true;
                }
        ));
        registerTransducer(COLON, Transducer.<TernaryOperatorContext, ExecutionException>checkAndPassChar(':').and(

                (inputChain, outputChain) -> {

                    if (outputChain.ternaryOperatorCondition())
                        outputChain.getScriptContext().setParsingPermission(true);

                    outputChain.getScriptContext().setParsingPermission(false);

                    return true;
                }
        ));
    }


    public static TernaryOperatorMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<TernaryStates> matrix = TransitionMatrix.<TernaryStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .withTemporaryState(BOOLEAN_EXPRESSION)
                .allowTransition(START, BOOLEAN_EXPRESSION)
                .allowTransition(BOOLEAN_EXPRESSION, MARK)
                .allowTransition(MARK, EXPRESSION)
                .allowTransition(EXPRESSION, COLON)
                .allowTransition(COLON, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH)
                .build();
        return new TernaryOperatorMachine(matrix, factory, exceptionThrower);

    }


}
