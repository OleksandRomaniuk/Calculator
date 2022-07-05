package src.fsm.brackets;

import com.google.common.base.Preconditions;
import fsm.ExceptionThrower;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.fsm.ShuntingYard;
import fsm.type.Value;

import java.util.function.BiConsumer;


/**
 * {@code BracketsMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing an expression inside the brackets.
 */

public final class BracketsMachine<O, E extends Exception> extends FiniteStateMachine<BracketsStates, O, E> {

    public static <O, E extends Exception> BracketsMachine<O, E> create(Transducer<O, E> transducer, ExceptionThrower<E> exceptionThrower) {

        Preconditions.checkNotNull(transducer);

        var matrix = TransitionMatrix.<BracketsStates>builder()
                .withStartState(BracketsStates.START)
                .withFinishState(BracketsStates.FINISH)
                .allowTransition(BracketsStates.START, BracketsStates.OPENING_BRACKET)
                .allowTransition(BracketsStates.OPENING_BRACKET, BracketsStates.EXPRESSION)
                .allowTransition(BracketsStates.EXPRESSION, BracketsStates.CLOSING_BRACKET)
                .allowTransition(BracketsStates.CLOSING_BRACKET, BracketsStates.FINISH)
                .build();

        return new BracketsMachine<>(matrix, transducer, exceptionThrower);
    }

    private BracketsMachine(TransitionMatrix<BracketsStates> matrix, Transducer<O, E> transducer, ExceptionThrower<E> exceptionThrower) {
        super(matrix, exceptionThrower, true);

        var consumer = (BiConsumer<ShuntingYard, Value>) ShuntingYard::pushOperand;

        registerTransducer(BracketsStates.START, Transducer.illegalTransition());
        registerTransducer(BracketsStates.OPENING_BRACKET, Transducer.checkAndPassChar('(') );
        registerTransducer(BracketsStates.EXPRESSION, transducer);
        registerTransducer(BracketsStates.CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(BracketsStates.FINISH, Transducer.autoTransition());
    }
}
