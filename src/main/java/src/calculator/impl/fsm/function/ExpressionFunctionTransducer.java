package src.calculator.impl.fsm.function;

import com.google.common.base.Preconditions;
import src.calculator.impl.fsm.Transducer;
import src.calculator.impl.fsm.util.Input;
import src.calculator.impl.fsm.util.FunctionHolder;
import src.calculator.impl.fsm.util.ResolvingException;
import src.calculator.impl.math.MathElementResolver;

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
