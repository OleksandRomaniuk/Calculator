package src.calculator.impl.fsm.expression;

import src.calculator.impl.fsm.FiniteStateMachine;
import src.calculator.impl.fsm.Transducer;
import src.calculator.impl.fsm.TransitionMatrix;
import src.calculator.impl.ShuntingYardTransducer;
import src.calculator.impl.fsm.util.ShuntingYardStack;
import src.calculator.impl.math.MathElement;
import src.calculator.impl.math.MathElementResolverFactory;

/**
 *
 *   ExpressionMachine is a realisation of {@link FiniteStateMachine}
 *   that implements a list of all possible transitions and actions when reading an expression
 *
 *
 */


public final class ExpressionMachine extends FiniteStateMachine<ExpressionStates, ShuntingYardStack> {

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

        registerTransducer(ExpressionStates.START, Transducer.illegalTransition());
        registerTransducer(ExpressionStates.OPERAND, new ShuntingYardTransducer(factory.create(MathElement.OPERAND)));
        registerTransducer(ExpressionStates.BINARY_OPERATOR, new BinaryOperatorTransducer());
        registerTransducer(ExpressionStates.FINISH, Transducer.autoTransition());
    }
}


