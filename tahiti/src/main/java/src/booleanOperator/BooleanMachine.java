package src.booleanOperator;


import fsm.ExceptionThrower;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.AbstractBinaryOperator;
import src.BinaryOperatorFactory;
import src.BooleanBinaryOperatorFactory;
import src.fsm.expression.BinaryOperatorTransducer;

import java.util.function.BiConsumer;

public final class BooleanMachine<O, E extends Exception> extends FiniteStateMachine<BooleanStates, O, E > {

    public static <O, E extends Exception> BooleanMachine<O, E> create(BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                                                                       Transducer<O, E> operandTransducer, ExceptionThrower<E> exceptionThrower) {

        var matrix = TransitionMatrix.<BooleanStates>builder()
                .withStartState(BooleanStates.START)
                .withFinishState(BooleanStates.FINISH)
                .allowTransition(BooleanStates.START, BooleanStates.OPERAND)
                .allowTransition(BooleanStates.OPERAND, BooleanStates.BOOLEAN_OPERATOR, BooleanStates.FINISH)
                .allowTransition(BooleanStates.BOOLEAN_OPERATOR, BooleanStates.OPERAND)

                .build();

        return new BooleanMachine<>(matrix, binaryConsumer, operandTransducer, exceptionThrower);
    }

    private BooleanMachine(TransitionMatrix<BooleanStates> matrix,
                           BiConsumer<O, AbstractBinaryOperator> binaryConsumer,
                           Transducer<O, E> operandTransducer,
                           ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        BinaryOperatorFactory booleanOperatorFactory = new BooleanBinaryOperatorFactory();

        registerTransducer(BooleanStates.START, Transducer.illegalTransition());
        registerTransducer(BooleanStates.OPERAND, operandTransducer);
        registerTransducer(BooleanStates.BOOLEAN_OPERATOR, new BinaryOperatorTransducer<>(booleanOperatorFactory, binaryConsumer));
        registerTransducer(BooleanStates.FINISH, Transducer.autoTransition());
    }
}



