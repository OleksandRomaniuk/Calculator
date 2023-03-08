package src.calucator.math;


import src.CharSequenceReader;

import src.calucator.fsm.ResolvingException;
import src.type.Value;

import java.util.Optional;


@FunctionalInterface
public interface MathElementResolver {

    Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException;
}
