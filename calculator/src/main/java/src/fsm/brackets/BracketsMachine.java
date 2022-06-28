package src.fsm.brackets;

import com.google.common.base.Preconditions;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.ShuntingYard;

import java.util.function.BiConsumer;

/**
 * BracketsMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state
 *
 */


public final class BracketsMachine<O> extends FiniteStateMachine<BracketsStates, O> {

    public static <O> BracketsMachine<O> create(Transducer<O> transducer) {

        Preconditions.checkNotNull(transducer);

        TransitionMatrix<BracketsStates> matrix = TransitionMatrix.<BracketsStates>builder()
                .withStartState(BracketsStates.START)
                .withFinishState(BracketsStates.FINISH)
                .allowTransition(BracketsStates.START, BracketsStates.OPENING_BRACKET)
                .allowTransition(BracketsStates.OPENING_BRACKET, BracketsStates.EXPRESSION)
                .allowTransition(BracketsStates.EXPRESSION, BracketsStates.CLOSING_BRACKET)
                .allowTransition(BracketsStates.CLOSING_BRACKET, BracketsStates.FINISH)
                .build();

        return new BracketsMachine<>(matrix, transducer);
    }

    private BracketsMachine(TransitionMatrix<BracketsStates> matrix, Transducer<O> transducer) {
        super(matrix, true);

        BiConsumer<ShuntingYard, Double> consumer = ShuntingYard::pushOperand;

        registerTransducer(BracketsStates.START, Transducer.illegalTransition());
        registerTransducer(BracketsStates.OPENING_BRACKET, Transducer.checkAndPassChar('(') );
        registerTransducer(BracketsStates.EXPRESSION, transducer);
        registerTransducer(BracketsStates.CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(BracketsStates.FINISH, Transducer.autoTransition());
    }
}
