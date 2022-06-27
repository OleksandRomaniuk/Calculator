package src.impl.resolvers;



import fsm.CharSequenceReader;
import fsm.ResolvingException;
import src.impl.fsm.number.NumberStateMachine;
import src.impl.math.MathElementResolver;

import java.util.Optional;



public class NumberResolver implements MathElementResolver {
    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        return NumberStateMachine.execute(inputChain);
    }
}
