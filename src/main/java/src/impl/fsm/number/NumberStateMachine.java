package src.impl.fsm.number;

import com.google.common.base.Preconditions;
import src.impl.fsm.FiniteStateMachine;
import src.impl.fsm.Transducer;
import src.impl.fsm.TransitionMatrix;

import static src.impl.fsm.number.NumberState.*;
/**
 * NumberStateMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state when reading a number
 *
 */

public final class NumberStateMachine extends FiniteStateMachine<NumberState, StringBuilder> {

    public static NumberStateMachine create() {
        TransitionMatrix<NumberState> matrix = TransitionMatrix.<NumberState>builder().
                withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(INTEGER_DIGIT, INTEGER_DIGIT, DOT, FINISH)
                .allowTransition(DOT, FLOATING_INTEGER)
                .allowTransition(FLOATING_INTEGER, FLOATING_INTEGER, FINISH)
                .build();

        return new NumberStateMachine(matrix);
    }

    private NumberStateMachine(TransitionMatrix<NumberState> matrix) {

        super(Preconditions.checkNotNull(matrix));

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(NEGATIVE_SIGN, new SymbolTransducer('-'));
        registerTransducer(INTEGER_DIGIT, new SymbolTransducer(Character::isDigit));
        registerTransducer(DOT, new SymbolTransducer('.'));
        registerTransducer(FLOATING_INTEGER, new SymbolTransducer(Character::isDigit));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

}

