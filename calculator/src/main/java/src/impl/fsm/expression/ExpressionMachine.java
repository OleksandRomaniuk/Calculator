package src.impl.fsm.expression;

import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.impl.ShuntingYardTransducer;
import src.impl.fsm.util.ShuntingYard;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;


import java.util.function.BiConsumer;

/**
 *
 *   ExpressionMachine is a realisation of {@link FiniteStateMachine}
 *   that implements a list of all possible transitions and actions when reading an expression
 *
 *
 */


public final class ExpressionMachine extends FiniteStateMachine<ExpressionStates, ShuntingYard> {

    public static ExpressionMachine create(MathElementResolverFactory factory) {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(ExpressionStates.START)
                .withFinishState(ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.START, ExpressionStates.OPERAND)
                .allowTransition(ExpressionStates.OPERAND, ExpressionStates.BINARY_OPERATOR, ExpressionStates.FINISH)
                .allowTransition(ExpressionStates.BINARY_OPERATOR, ExpressionStates.OPERAND)

                .build();

        return new ExpressionMachine(matrix, factory);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.OPERAND, new ShuntingYardTransducer(factory.create(MathElement.OPERAND),consumer));
        registerTransducer(ExpressionStates.BINARY_OPERATOR, new BinaryOperatorTransducer());
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
    }
}


