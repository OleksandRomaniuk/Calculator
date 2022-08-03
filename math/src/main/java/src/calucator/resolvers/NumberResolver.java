package src.calucator.resolvers;


import src.CharSequenceReader;

import src.calucator.fsm.ResolvingException;
import src.calucator.fsm.number.NumberStateMachine;
import src.calucator.math.MathElementResolver;
import src.type.Value;

import java.util.Optional;

/**
 * Implementation of {@link MathElementResolver} that resolve input chain for {@link NumberStateMachine}.
 */

public class NumberResolver implements MathElementResolver {
    @Override
    public Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException {

        return NumberStateMachine.execute(inputChain, errorMessage -> {throw new ResolvingException(errorMessage);});
    }
}