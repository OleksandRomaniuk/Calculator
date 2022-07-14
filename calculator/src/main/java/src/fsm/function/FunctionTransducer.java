package src.fsm.function;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.ResolvingException;
import src.Transducer;
import src.datastructures.ShuntingYard;
import src.math.MathElementResolver;
import src.type.Value;

import java.util.Optional;

/**
 * {@code FunctionTransducer} is an implementation of {@link Transducer}
 * that produce a number as a result of applying a function
 * to FunctionMachine.
 */

public class FunctionTransducer implements Transducer<ShuntingYard, ResolvingException> {

    private final MathElementResolver resolver;

    public FunctionTransducer(MathElementResolver resolver) {
        this.resolver = Preconditions.checkNotNull(resolver);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYard outputChain) throws ResolvingException {

        Optional<Value> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()) {
            outputChain.pushOperand(resolve.get());

            return true;
        }
        return false;
    }
}
