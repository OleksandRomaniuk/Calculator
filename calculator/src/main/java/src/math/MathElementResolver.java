package src.math;


import src.CharSequenceReader;
import src.ResolvingException;
import src.type.Value;

import java.util.Optional;

/**
 * {@code MathElementResolver} is a functional interface that can be used to
 * implement any code that can resolve {@link CharSequenceReader} to {@link Double}
 * and throws {@link ResolvingException}.
 */

@FunctionalInterface
public interface MathElementResolver {

    Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException;
}
