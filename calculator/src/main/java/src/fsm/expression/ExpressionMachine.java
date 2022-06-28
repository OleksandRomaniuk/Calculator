package src.fsm.expression;



import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.fsm.util.PrioritizedBinaryOperator;

import java.util.function.BiConsumer;

/**
 *
 *   ExpressionMachine is a realisation of {@link FiniteStateMachine}
 *   that implements a list of all possible transitions and actions when reading an expression
 *
 *
 */


public final class  ExpressionMachine<O> extends FiniteStateMachine<ExpressionStates, O> {

    public static <O> ExpressionMachine<O> create(BiConsumer<O, PrioritizedBinaryOperator> binaryConsumer,
                                                  Transducer<O> operandTransducer) {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(ExpressionStates.START)
                .withFinishState(ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.START, ExpressionStates.OPERAND)
                .allowTransition(ExpressionStates.OPERAND, ExpressionStates.BINARY_OPERATOR, ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.BINARY_OPERATOR, ExpressionStates.OPERAND)

                .build();

        return new ExpressionMachine<>(matrix, binaryConsumer, operandTransducer);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix,
                              BiConsumer<O, PrioritizedBinaryOperator> binaryConsumer,
                              Transducer<O> operandTransducer) {
        super(matrix, true);

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.OPERAND, operandTransducer);
        registerTransducer(ExpressionStates.BINARY_OPERATOR, new BinaryOperatorTransducer<>(binaryConsumer));
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
    }
}


