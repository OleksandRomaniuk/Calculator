package src.programStructure.ternary;


import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.programStructure.booleanOperand.BooleanTransducer;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramFactory;

import static src.programStructure.ternary.TernaryStates.*;


public final class TernaryOperatorMachine extends FiniteStateMachine<TernaryStates, TernaryOperatorContext, ExecutionException> {

    private TernaryOperatorMachine(TransitionMatrix<TernaryStates> matrix, ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower, true);


        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.autoTransition());

        registerTransducer(BOOLEAN_EXPRESSION, new BooleanTransducer(factory.create(ProgramElement.BOOLEAN_EXPRESSION)));

        registerTransducer(MARK, Transducer.<TernaryOperatorContext, ExecutionException>checkAndPassChar('?').and(
                (inputChain, outputChain) -> {
                    if (!outputChain.ternaryOperatorCondition()) {
                        outputChain.getScriptContext().setParsingPermission(true);

                    }

                    return true;
                }
        ));

        registerTransducer(EXPRESSION, new (factory.create(ProgramElement.EXPRESSION)))

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
