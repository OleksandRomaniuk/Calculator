package src.impl.fsm.operand;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.ResolvingException;
import fsm.Transducer;
import src.impl.fsm.util.ShuntingYard;
import src.impl.math.MathElementResolver;

import java.util.Optional;

/**
 * {@code NumberTransducer} is an implementation of {@link Transducer}
 * that produce a number as a result of reading input
 * to {@link ShuntingYard} output.
 */

public class NumberTransducer implements Transducer<ShuntingYard> {

    private final MathElementResolver resolver;

    public NumberTransducer(MathElementResolver resolver) {
        this.resolver = Preconditions.checkNotNull(resolver);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ShuntingYard outputChain) throws ResolvingException {

        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){
            outputChain.pushOperand(resolve.get());

            return true;
        }
        return false;
    }

}
