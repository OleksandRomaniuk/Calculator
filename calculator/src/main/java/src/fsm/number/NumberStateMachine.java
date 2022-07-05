package src.fsm.number;

import com.google.common.base.Preconditions;
import fsm.*;
import fsm.identifier.SymbolTransducer;
import fsm.type.DoubleValue;
import fsm.type.Value;


import java.util.Optional;

import static src.fsm.number.NumberStates.*;


/**
 * {@code NumberStateMachine} is a realisation of {@link FiniteStateMachine}
 * for parsing a number.
 */

public final class NumberStateMachine<E extends Exception> extends FiniteStateMachine<NumberStates, StringBuilder, E> {

    public static <E extends Exception> Optional<Value> execute(CharSequenceReader inputChain, ExceptionThrower<E> exceptionThrower) throws E {
        var stringBuilder = new StringBuilder();

        var numberMachine = NumberStateMachine.create(exceptionThrower);

        if (numberMachine.run(inputChain, stringBuilder)) {

            Value number = new DoubleValue(Double.parseDouble(stringBuilder.toString()));

            return Optional.of(number);
        }

        return Optional.empty();
    }

    public static <E extends Exception> NumberStateMachine<E> create(ExceptionThrower<E> exceptionThrower) {
        var matrix = TransitionMatrix.<NumberStates>builder().
                withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(INTEGER_DIGIT, INTEGER_DIGIT, DOT, FINISH)
                .allowTransition(DOT, FLOATING_INTEGER)
                .allowTransition(FLOATING_INTEGER, FLOATING_INTEGER, FINISH)
                .build();

        return new NumberStateMachine<>(matrix, exceptionThrower);
    }

    private NumberStateMachine(TransitionMatrix<NumberStates> matrix, ExceptionThrower<E> exceptionThrower) {

        super(Preconditions.checkNotNull(matrix), exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(NEGATIVE_SIGN, new SymbolTransducer<>('-'));
        registerTransducer(INTEGER_DIGIT, new SymbolTransducer<>(Character::isDigit));
        registerTransducer(DOT, new SymbolTransducer<>('.'));
        registerTransducer(FLOATING_INTEGER, new SymbolTransducer<>(Character::isDigit));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

}

