package src.fsm.operand;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.ResolvingException;
import src.Transducer;
import src.fsm.function.ShuntingYard;
import src.math.MathElementResolver;
import src.type.Value;

import java.util.Optional;

/**
 * {@code NumberTransducer} is an implementation of {@link Transducer}
 * that produce a number as a result of reading input
 * to {@link ShuntingYard} output.
 */

public class NumberTransducer implements Transducer<ShuntingYard, ResolvingException> {

    private final MathElementResolver resolver;

    public NumberTransducer(MathElementResolver resolver) {
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
