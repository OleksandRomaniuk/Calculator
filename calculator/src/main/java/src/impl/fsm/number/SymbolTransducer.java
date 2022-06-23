package src.impl.fsm.number;

import com.google.common.base.Preconditions;
import fsm.Input;
import fsm.Transducer;

import java.util.function.Predicate;

/**
 *
 * Implementation of {@link Transducer} for {@link StringBuilder} output.
 *
 */




public class SymbolTransducer implements Transducer<StringBuilder> {

    private final Predicate<Character> condition;

    public SymbolTransducer(Predicate<Character> condition) {

        this.condition = Preconditions.checkNotNull(condition);
    }

    SymbolTransducer(char symbol) {

        this(character -> symbol == character);
    }

    @Override
    public boolean doTransition(Input inputChain, StringBuilder outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        boolean nextCharIsAvailable = inputChain.canRead();

        if (nextCharIsAvailable && condition.test(inputChain.read())) {

            outputChain.append(inputChain.read());

            inputChain.incrementPosition();

            return true;
        }

        return false;
    }
}