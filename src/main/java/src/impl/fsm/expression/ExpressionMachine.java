package src.impl.fsm.expression;

import src.impl.fsm.FiniteStateMachine;
import src.impl.fsm.Transducer;
import src.impl.fsm.TransitionMatrix;
import src.impl.ShuntingYardTransducer;
import src.impl.fsm.util.ShuntingYardStack;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;

import static src.impl.fsm.expression.ExpressionStates.*;

public final class ExpressionMachine extends FiniteStateMachine<ExpressionStates, ShuntingYardStack> {

    public static ExpressionMachine create(MathElementResolverFactory factory) {

        TransitionMatrix<ExpressionStates> matrix = TransitionMatrix.<ExpressionStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, OPERAND)
                .allowTransition(OPERAND, BINARY_OPERATOR, FINISH)
                .allowTransition(BINARY_OPERATOR, OPERAND)

                .build();

        return new ExpressionMachine(matrix, factory);
    }

    private ExpressionMachine(TransitionMatrix<ExpressionStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(OPERAND, new ShuntingYardTransducer(factory.create(MathElement.OPERAND)));
        registerTransducer(BINARY_OPERATOR, new BinaryOperatorTransducer());
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}


