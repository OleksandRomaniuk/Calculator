package fsm;


import src.impl.fsm.util.ResolvingException;

/**
 * Basic interface for navigating the program
 * @param <O>
 */
@FunctionalInterface
public interface Transducer<O> {

    boolean doTransition(Input inputChain, O outputChain) throws ResolvingException, ResolvingException;

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
    default Transducer<O> and (Transducer<O> transducer){
        return (inputChain, outputChain) -> {

            if (doTransition(inputChain, outputChain)) {
                return transducer.doTransition(inputChain, outputChain);
            }

            return false;
        };
    }
}
