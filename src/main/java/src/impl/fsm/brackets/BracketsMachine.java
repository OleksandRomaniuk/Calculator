package src.impl.fsm.brackets;

import src.impl.fsm.FiniteStateMachine;
import src.impl.fsm.Transducer;
import src.impl.fsm.TransitionMatrix;
import src.impl.ShuntingYardTransducer;
import src.impl.fsm.util.ShuntingYardStack;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;

import static src.impl.fsm.brackets.BracketsStates.*;

/**
 * BracketsMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state
 *
 */


public final class BracketsMachine extends FiniteStateMachine<BracketsStates, ShuntingYardStack> {

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

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('(') );
        registerTransducer(EXPRESSION, new ShuntingYardTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}
