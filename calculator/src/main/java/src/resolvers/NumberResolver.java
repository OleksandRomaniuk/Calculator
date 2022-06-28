package src.resolvers;



import fsm.CharSequenceReader;
import fsm.ResolvingException;
import src.fsm.number.NumberStateMachine;
import src.math.MathElementResolver;


import java.util.Optional;



public class NumberResolver implements MathElementResolver {
    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        return NumberStateMachine.execute(inputChain);
    }
}
