package src.calculator.impl.fsm.brackets;

import src.calculator.impl.ShuntingYardTransducer;
import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;

import src.calculator.impl.fsm.util.ShuntingYard;
import src.calculator.impl.math.MathElement;
import src.calculator.impl.math.MathElementResolverFactory;

import java.util.function.BiConsumer;

import static src.calculator.impl.fsm.brackets.BracketsStates.*;

/**
 * BracketsMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state
 *
 */


public final class BracketsMachine extends FiniteStateMachine<BracketsStates, ShuntingYard> {

    public static BracketsMachine create(MathElementResolverFactory factory) {

        TransitionMatrix<BracketsStates> matrix = TransitionMatrix.<BracketsStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, OPENING_BRACKET)
                .allowTransition(OPENING_BRACKET, EXPRESSION)
                .allowTransition(EXPRESSION, CLOSING_BRACKET)
                .allowTransition(CLOSING_BRACKET, FINISH)
                .build();

        return new BracketsMachine(matrix, factory);
    }

    private BracketsMachine(TransitionMatrix<BracketsStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('(') );
        registerTransducer(EXPRESSION, new ShuntingYardTransducer(factory.create(MathElement.EXPRESSION),consumer));
        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}
