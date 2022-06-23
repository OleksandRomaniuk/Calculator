package src.impl.fsm.operand;

import com.google.common.base.Preconditions;
import fsm.Input;
import fsm.Transducer;
import src.impl.fsm.util.ResolvingException;
import src.impl.fsm.util.ShuntingYard;
import src.impl.math.MathElementResolver;


import java.util.Optional;

public class NumberTransducer implements Transducer<ShuntingYard> {

    private final MathElementResolver resolver;

    NumberTransducer(MathElementResolver resolver) {
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
