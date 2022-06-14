package src.impl.fsm.function;

import com.google.common.base.Preconditions;
import src.impl.fsm.Transducer;
import src.impl.fsm.util.Input;
import src.impl.fsm.util.FunctionHolder;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolver;

import java.util.Optional;

public class ExpressionFunctionTransducer implements Transducer<FunctionHolder> {

    private final MathElementResolver resolver;

    ExpressionFunctionTransducer(MathElementResolver resolver) {

        this.resolver = Preconditions.checkNotNull(resolver);
    }

    @Override
    public boolean doTransition(Input inputChain, FunctionHolder outputChain) throws ResolvingException {

        Optional<Double> resolve = resolver.resolve(inputChain);

        if (resolve.isPresent()){

            outputChain.setArgument(resolve.get());

            return true;
        }

        return false;
    }
}
