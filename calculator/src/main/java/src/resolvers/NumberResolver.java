package src.resolvers;


import fsm.CharSequenceReader;
import src.ResolvingException;
import src.fsm.number.NumberStateMachine;
import src.math.MathElementResolver;
import fsm.type.Value;

import java.util.Optional;

/**
 * {@code NumberResolver} is an implementation of {@link MathElementResolver} that
 * resolve input chain for {@link NumberStateMachine}.
 */

public class NumberResolver implements MathElementResolver {
    @Override
    public Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException {

        return NumberStateMachine.execute(inputChain, errorMessage -> {throw new ResolvingException(errorMessage);});
    }
}
