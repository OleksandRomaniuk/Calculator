package src.impl;

import com.google.common.base.Preconditions;
import src.impl.fsm.Transducer;
import src.impl.fsm.util.Input;
import src.impl.fsm.util.ResolvingException;
import src.impl.fsm.util.ShuntingYardStack;
import src.impl.math.MathElementResolver;

import java.util.Optional;

/**
 *
 *
 */


public class ShuntingYardTransducer implements Transducer<ShuntingYardStack> {

    private final MathElementResolver resolver;

    public ShuntingYardTransducer(MathElementResolver resolver) {
        this.resolver = Preconditions.checkNotNull(resolver);
    }

    @Override
    public boolean doTransition(Input inputChain, ShuntingYardStack outputChain) throws ResolvingException {
        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){
            outputChain.pushOperand(resolve.get());

            return true;
        }
        return false;
    }
}
