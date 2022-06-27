package src.impl.fsm.function;

import com.google.common.base.Preconditions;
import src.fsm.Input;
import src.fsm.Transducer;
import src.impl.fsm.util.ResolvingException;
import src.impl.fsm.util.ShuntingYard;
import src.impl.math.MathElementResolver;


import java.util.Optional;

/**
 *
 * Implementation of {@link Transducer} for {@link StringBuilder} output.
 *
 */

public class FunctionTransducer implements Transducer<ShuntingYard> {

    private final MathElementResolver resolver;

    public FunctionTransducer(MathElementResolver resolver) {
        this.resolver = Preconditions.checkNotNull(resolver);
    }

    @Override
    public boolean doTransition(Input inputChain, ShuntingYard outputChain) throws ResolvingException {

        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){
            outputChain.pushOperand(resolve.get());

            return true;
        }
        return false;
    }
}




