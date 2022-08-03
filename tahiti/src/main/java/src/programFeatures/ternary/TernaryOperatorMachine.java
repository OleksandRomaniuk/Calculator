package src.programFeatures.ternary;

import src.*;
import src.tahiti.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static src.programFeatures.ternary.TernaryStates.*;


/**
 * Realisation of {@link FiniteStateMachine} that used for parsing and executing ternary operator.
 */

public final class TernaryOperatorMachine extends FiniteStateMachine<TernaryStates, TernaryOperatorContext, ExecutionException> {

    private TernaryOperatorMachine(TransitionMatrix<TernaryStates> matrix, ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.<TernaryOperatorContext, ExecutionException>autoTransition()
                .and((inputChain, outputChain) -> {

                    outputChain.prohibitParseOnly();

                    return true;
                }));

        registerTransducer(BOOLEAN_EXPRESSION, new RelationalExpressionTransducer(factory.create(ProgramElement.BOOLEAN_EXPRESSION)));

        registerTransducer(COLON, Transducer.<TernaryOperatorContext, ExecutionException>checkAndPassChar(':').and(
                (inputChain, outputChain) -> {

                    if (outputChain.ternaryOperatorCondition()) {

                        outputChain.setParseOnlyMode();

                    } else {

                        outputChain.prohibitParseOnly();
                    }

                    return true;
                }
        ));

        registerTransducer(MARK, Transducer.<TernaryOperatorContext, ExecutionException>checkAndPassChar('?').and(
                (inputChain, outputChain) -> {
                    if (!outputChain.ternaryOperatorCondition()) {

                        outputChain.setParseOnlyMode();

                    }

                    return true;
                }
        ));

        registerTransducer(EXPRESSION, new TernaryExpressionOperatorTransducer(factory.create(ProgramElement.EXPRESSION)));

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
