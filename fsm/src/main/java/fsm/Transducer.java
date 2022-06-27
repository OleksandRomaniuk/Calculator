package fsm;

/**
 * {@code Transducer} is a functional interface that can be used to
 * produce {@param <O>} output based on a given input
 * and potentially throws a {@link ResolvingException}.
 */

@FunctionalInterface
public interface Transducer<O> {

    boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ResolvingException;

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
