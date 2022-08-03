package src.calucator.fsm.number;


import com.google.common.base.Preconditions;
import src.*;
import src.identifier.SymbolTransducer;
import src.type.DoubleValue;
import src.type.Value;

import java.util.Optional;

import static src.calucator.fsm.number.NumberStates.*;


/**
 * Realisation of {@link FiniteStateMachine} for parsing a number.
 */

public final class NumberStateMachine<E extends Exception> extends FiniteStateMachine<NumberStates, StringBuilder, E> {

    private NumberStateMachine(TransitionMatrix<NumberStates> matrix, ExceptionThrower<E> exceptionThrower) {

        super(Preconditions.checkNotNull(matrix), exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(NEGATIVE_SIGN, new SymbolTransducer<>('-'));
        registerTransducer(INTEGER_DIGIT, new SymbolTransducer<>(Character::isDigit));
        registerTransducer(DOT, new SymbolTransducer<>('.'));
        registerTransducer(FLOATING_INTEGER, new SymbolTransducer<>(Character::isDigit));
        registerTransducer(FINISH, Transducer.autoTransition());
    }

    public static <E extends Exception> Optional<Value> execute(CharSequenceReader inputChain, ExceptionThrower<E> exceptionThrower) throws E {
        StringBuilder stringBuilder = new StringBuilder();

        NumberStateMachine<E> numberMachine = NumberStateMachine.create(exceptionThrower);

        if (numberMachine.run(inputChain, stringBuilder)) {

            Value number = new DoubleValue(Double.parseDouble(stringBuilder.toString()));

            return Optional.of(number);
        }

        return Optional.empty();
    }

    public static <E extends Exception> NumberStateMachine<E> create(ExceptionThrower<E> exceptionThrower) {
        TransitionMatrix<NumberStates> matrix = TransitionMatrix.<NumberStates>builder().
                withStartState(START)
                .withFinishState(FINISH)
                .withTemporaryState(NEGATIVE_SIGN)
                .allowTransition(START, NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(NEGATIVE_SIGN, INTEGER_DIGIT)
                .allowTransition(INTEGER_DIGIT, INTEGER_DIGIT, DOT, FINISH)
                .allowTransition(DOT, FLOATING_INTEGER)
                .allowTransition(FLOATING_INTEGER, FLOATING_INTEGER, FINISH)
                .build();

        return new NumberStateMachine<>(matrix, exceptionThrower);
    }

}




