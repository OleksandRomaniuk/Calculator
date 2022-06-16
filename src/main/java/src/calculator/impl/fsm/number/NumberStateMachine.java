package src.calculator.impl.fsm.number;

import com.google.common.base.Preconditions;
import src.calculator.impl.fsm.FiniteStateMachine;
import src.calculator.impl.fsm.Transducer;
import src.calculator.impl.fsm.TransitionMatrix;

/**
 * NumberStateMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state when reading a number
 *
 */

public final class NumberStateMachine extends FiniteStateMachine<NumberState, StringBuilder> {

    public static NumberStateMachine create() {
        TransitionMatrix<NumberState> matrix = TransitionMatrix.<NumberState>builder().
                withStartState(NumberState.START)
                .withFinishState(NumberState.FINISH)
                .allowTransition(NumberState.START, NumberState.NEGATIVE_SIGN, NumberState.INTEGER_DIGIT)
                .allowTransition(NumberState.NEGATIVE_SIGN, NumberState.INTEGER_DIGIT)
                .allowTransition(NumberState.INTEGER_DIGIT, NumberState.INTEGER_DIGIT, NumberState.DOT, NumberState.FINISH)
                .allowTransition(NumberState.DOT, NumberState.FLOATING_INTEGER)
                .allowTransition(NumberState.FLOATING_INTEGER, NumberState.FLOATING_INTEGER, NumberState.FINISH)
                .build();

        return new NumberStateMachine(matrix);
    }

    private NumberStateMachine(TransitionMatrix<NumberState> matrix) {

        super(Preconditions.checkNotNull(matrix));

        registerTransducer(NumberState.START, Transducer.illegalTransition());
        registerTransducer(NumberState.NEGATIVE_SIGN, new SymbolTransducer('-'));
        registerTransducer(NumberState.INTEGER_DIGIT, new SymbolTransducer(Character::isDigit));
        registerTransducer(NumberState.DOT, new SymbolTransducer('.'));
        registerTransducer(NumberState.FLOATING_INTEGER, new SymbolTransducer(Character::isDigit));
        registerTransducer(NumberState.FINISH, Transducer.autoTransition());
    }

}

