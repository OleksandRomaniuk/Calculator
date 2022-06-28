package src;



import fsm.CharSequenceReader;
import fsm.Transducer;

import java.util.function.Predicate;



public class SymbolTransducer implements Transducer<StringBuilder> {

    private final Predicate<Character> condition;

    public SymbolTransducer(Predicate<Character> condition) {

        this.condition =condition;
    }

    public SymbolTransducer(char symbol) {

        this(character -> symbol == character);
    }



    @Override
    public boolean doTransition(CharSequenceReader inputChain, StringBuilder outputChain) {

        boolean nextCharIsAvailable = inputChain.canRead();

        if (nextCharIsAvailable && condition.test(inputChain.read())) {

            outputChain.append(inputChain.read());

            inputChain.incrementPosition();

            return true;
        }

        return false;
    }
}
