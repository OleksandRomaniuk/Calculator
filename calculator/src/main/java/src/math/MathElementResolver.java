package src.math;


import fsm.CharSequenceReader;
import src.ResolvingException;
import fsm.type.Value;

import java.util.Optional;

@FunctionalInterface
public interface MathElementResolver {

    Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException;
}
