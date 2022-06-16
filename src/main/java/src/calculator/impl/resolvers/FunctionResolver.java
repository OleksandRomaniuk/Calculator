package src.calculator.impl.resolvers;

import src.calculator.impl.fsm.function.FunctionFactory;
import src.calculator.impl.fsm.function.FunctionMachine;
import src.calculator.impl.fsm.util.Input;
import src.calculator.impl.fsm.util.FunctionHolder;
import src.calculator.impl.fsm.util.ResolvingException;
import src.calculator.impl.math.MathElementResolver;
import src.calculator.impl.math.MathElementResolverFactory;

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
