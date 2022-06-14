package src.impl.fsm;

import src.impl.fsm.util.Input;
import src.impl.fsm.util.ResolvingException;

@FunctionalInterface
public interface Transducer<O> {

    boolean doTransition(Input inputChain, O outputChain) throws ResolvingException;

    static <O> Transducer<O> autoTransition() {

        return (inputChain, outputChain) -> true;
    }

    static <O> Transducer<O> illegalTransition() {

        return (inputChain, outputChain) -> {

            throw new IllegalStateException("Transition to START make no sense");
        };
    }

    static <O> Transducer<O> checkAndPassChar(char character) {

        return (inputChain, outputChain) -> {
            if (inputChain.canRead() && inputChain.read() == character) {
                inputChain.incrementPosition();
                return true;
            }
            return false;
        };
    }
}
