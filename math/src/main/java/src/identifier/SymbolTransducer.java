package src.identifier;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;


import java.util.function.Predicate;

/**
 * Implementation of {@link Transducer}
 * that produce a symbol to {@link StringBuilder} output.
 */

public class SymbolTransducer<E extends Exception> implements Transducer<StringBuilder, E> {

    private final Predicate<Character> condition;

    public SymbolTransducer(Predicate<Character> condition) {

        this.condition = Preconditions.checkNotNull(condition);
    }

    public SymbolTransducer(char symbol) {

        this(character -> symbol == character);
    }



    @Override
    public boolean doTransition(CharSequenceReader inputChain, StringBuilder outputChain) {

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
