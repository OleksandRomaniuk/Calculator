package src.resolvers;



import src.fsm.Input;
import src.impl.fsm.function.FunctionFactory;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;

import java.util.Optional;

public class ProcedureResolver implements MathElementResolver {

    private final MathElementResolverFactory elementResolverFactory;

    private final FunctionFactory functionFactory = new FunctionFactory();

    public ProcedureResolver(MathElementResolverFactory elementResolverFactory) {
        this.elementResolverFactory = elementResolverFactory;
    }

    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {
        return Optional.empty();
    }
}
