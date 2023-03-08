package src.calucator.fsm.expression;


import src.*;
import src.operators.AbstractBinaryOperator;
import src.operators.BinaryOperatorFactory;

import java.util.function.BiConsumer;

/**
 * Implementation of {@link FiniteStateMachine} which is intended to process
 * the general structure of math expression — operands and binary operators.
 */

public final class ExpressionMachine<O, E extends Exception> extends FiniteStateMachine<ExpressionStates, O, E> {

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix,
                              BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                              BinaryOperatorFactory binaryOperatorFactory,
                              Transducer<O, E> operandTransducer,
                              ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.OPERAND, operandTransducer);
        registerTransducer(ExpressionStates.BINARY_OPERATOR, new BinaryOperatorTransducer<>(binaryOperatorFactory, binaryConsumer));
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
    }

    public static <O, E extends Exception> ExpressionMachine<O, E> create(BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                                                                          BinaryOperatorFactory binaryOperatorFactory,
                                                                          Transducer<O, E> operandTransducer, ExceptionThrower<E> exceptionThrower) {
        var matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(ExpressionStates.START)
                .withFinishState(ExpressionStates.FINISH)
                .withTemporaryState(ExpressionStates.START)
                .allowTransition(ExpressionStates.START, ExpressionStates.OPERAND)
                .allowTransition(ExpressionStates.OPERAND, ExpressionStates.BINARY_OPERATOR, ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.BINARY_OPERATOR, ExpressionStates.OPERAND)

                .build();

        return new ExpressionMachine<>(matrix, binaryConsumer, binaryOperatorFactory, operandTransducer, exceptionThrower);
    }
}



