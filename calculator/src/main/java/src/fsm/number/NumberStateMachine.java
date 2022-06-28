package src.fsm.number;

import com.google.common.base.Preconditions;
import fsm.*;
import src.SymbolTransducer;

import java.util.Optional;

import static src.fsm.number.NumberStates.*;



/**
 * {@code NumberStateMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a number.
 */

public final class NumberStateMachine extends FiniteStateMachine<NumberStates, StringBuilder> {

    public static Optional<Double> execute(CharSequenceReader inputChain) throws ResolvingException {
        StringBuilder stringBuilder = new StringBuilder();

        NumberStateMachine numberMachine = NumberStateMachine.create();

        if (numberMachine.run(inputChain, stringBuilder)) {

            return Optional.of(Double.parseDouble(stringBuilder.toString()));
        }

        return Optional.empty();
    }

    public static NumberStateMachine create() {
        TransitionMatrix<NumberStates> matrix = TransitionMatrix.<NumberStates>builder().
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

    private NumberStateMachine(TransitionMatrix<NumberStates> matrix) {

        super(Preconditions.checkNotNull(matrix));

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(NEGATIVE_SIGN, new SymbolTransducer('-'));
        registerTransducer(INTEGER_DIGIT, new SymbolTransducer(Character::isDigit));
        registerTransducer(DOT, new SymbolTransducer('.'));
        registerTransducer(FLOATING_INTEGER, new SymbolTransducer(Character::isDigit));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

}

