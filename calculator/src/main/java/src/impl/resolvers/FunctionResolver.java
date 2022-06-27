package src.impl.resolvers;



import fsm.CharSequenceReader;
import fsm.ResolvingException;
import src.impl.fsm.calculator.DetachedShuntingYardTransducer;
import src.impl.fsm.function.FunctionFactory;
import src.impl.fsm.function.FunctionMachine;
import src.impl.fsm.util.FunctionHolder;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;

import java.util.Optional;

/**
 * {@code FunctionResolver} is an implementation of {@link MathElementResolver} that
 * resolve input chain for {@link FunctionMachine}.
 */

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
