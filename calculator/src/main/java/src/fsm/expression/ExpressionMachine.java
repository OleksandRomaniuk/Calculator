package src.fsm.expression;


import src.*;

import java.util.function.BiConsumer;

/**
 * {@code ExpressionMachine} implementation of {@link FiniteStateMachine} which is intended to process
 * the general structure of math expression — operands and binary operators.
 * Operand may be a number, an expression in brackets, or a function —
 * see OperandMachine for details.
 */

public final class ExpressionMachine<O, E extends Exception> extends FiniteStateMachine<ExpressionStates, O, E> {

    public static <O, E extends Exception> ExpressionMachine<O, E> create(BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                                                                          BinaryOperatorFactory factory,
                                                                          Transducer<O, E> operandTransducer,
                                                                          ExceptionThrower<E> exceptionThrower) {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(ExpressionStates.START)
                .withFinishState(ExpressionStates.FINISH)
                .withTemporaryState(ExpressionStates.START, ExpressionStates.BINARY_OPERATOR)
                .allowTransition(ExpressionStates.START, ExpressionStates.OPERAND)
                .allowTransition(ExpressionStates.OPERAND, ExpressionStates.BINARY_OPERATOR, ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.BINARY_OPERATOR, ExpressionStates.OPERAND)

                .build();

        return new ExpressionMachine<>(matrix, binaryConsumer, factory, operandTransducer, exceptionThrower);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix,
                              BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                              BinaryOperatorFactory factory,
                              Transducer<O, E> operandTransducer,
                              ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.OPERAND, operandTransducer);
        registerTransducer(ExpressionStates.BINARY_OPERATOR, new BinaryOperatorTransducer<>(factory, binaryConsumer));
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
    }
}



