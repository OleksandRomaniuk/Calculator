package src.math;



import fsm.CharSequenceReader;
import fsm.ResolvingException;

import java.util.Optional;


@FunctionalInterface
public interface MathElementResolver {

    Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException;
}
