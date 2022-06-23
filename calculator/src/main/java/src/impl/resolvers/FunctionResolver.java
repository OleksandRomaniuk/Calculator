package src.impl.resolvers;

import fsm.Input;
import src.impl.fsm.function.FunctionFactory;
import src.impl.fsm.function.FunctionMachine;
import src.impl.fsm.util.FunctionHolder;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;


import java.util.Optional;

public class FunctionResolver implements MathElementResolver {

    private final MathElementResolverFactory elementResolverFactory;

    private final FunctionFactory functionFactory = new FunctionFactory();

    public FunctionResolver(MathElementResolverFactory factory) {

        this.elementResolverFactory = factory;
    }

    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {

        FunctionHolder holder = new FunctionHolder();

        FunctionMachine functionMachine = FunctionMachine.create(elementResolverFactory);

        if (functionMachine.run(inputChain, holder)) {

            return Optional.ofNullable(functionFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }

}
