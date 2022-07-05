package src.fsm.expression;


import fsm.ExceptionThrower;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.AbstractBinaryOperator;
import src.BinaryOperatorFactory;
import src.DoubleBinaryOperatorFactory;


import java.util.function.BiConsumer;

/**
 * {@code ExpressionMachine} implementation of {@link FiniteStateMachine} which is intended to process
 * the general structure of math expression — operands and binary operators.
 * Operand may be a number, an expression in brackets, or a function —
 * see OperandMachine for details.
 */

public final class  ExpressionMachine<O, E extends Exception> extends FiniteStateMachine<ExpressionStates, O, E > {

    public static <O, E extends Exception> ExpressionMachine<O, E> create(BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                                                                          Transducer<O, E> operandTransducer, ExceptionThrower<E> exceptionThrower) {

        var matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(ExpressionStates.START)
                .withFinishState(ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.START, ExpressionStates.OPERAND)
                .allowTransition(ExpressionStates.OPERAND, ExpressionStates.BINARY_OPERATOR, ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.BINARY_OPERATOR, ExpressionStates.OPERAND)

                .build();

        return new ExpressionMachine<>(matrix, binaryConsumer, operandTransducer, exceptionThrower);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix,
                              BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                              Transducer<O, E> operandTransducer,
                              ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        BinaryOperatorFactory doubleBinaryOperatorFactory = new DoubleBinaryOperatorFactory();

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.OPERAND, operandTransducer);
        registerTransducer(ExpressionStates.BINARY_OPERATOR, new BinaryOperatorTransducer<>(doubleBinaryOperatorFactory, binaryConsumer));
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
    }
}



