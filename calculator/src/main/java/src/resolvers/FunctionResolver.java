package src.resolvers;



import fsm.CharSequenceReader;
import fsm.ResolvingException;

import src.fsm.calculator.DetachedShuntingYardTransducer;
import src.fsm.function.FunctionFactory;

import src.fsm.function.FunctionMachine;
import src.fsm.util.FunctionHolder;
import src.math.MathElement;
import src.math.MathElementResolver;
import src.math.MathElementResolverFactory;


import java.util.Optional;



public class FunctionResolver implements MathElementResolver {

    private final MathElementResolverFactory elementResolverFactory;

    private final FunctionFactory functionFactory = new FunctionFactory();

    public FunctionResolver(MathElementResolverFactory factory) {

        this.elementResolverFactory = factory;
    }

    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        FunctionHolder holder = new FunctionHolder();

        FunctionMachine<FunctionHolder> functionMachine = FunctionMachine.create(new DetachedShuntingYardTransducer<>(
                MathElement.EXPRESSION, FunctionHolder::setArgument, elementResolverFactory), FunctionHolder::setFunctionName);

        if (functionMachine.run(inputChain, holder)) {

            return Optional.ofNullable(functionFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }

}
