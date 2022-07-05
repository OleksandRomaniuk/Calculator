package src.resolvers;



import fsm.CharSequenceReader;
import src.ResolvingException;
import src.fsm.function.FunctionHolder;
import src.fsm.calculator.DetachedShuntingYardTransducer;
import src.fsm.function.FunctionFactory;
import src.fsm.function.FunctionMachine;
import src.math.MathElement;
import src.math.MathElementResolver;
import src.math.MathElementResolverFactory;
import fsm.type.Value;

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
    public Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException {

        var holder = new FunctionHolder();

        var functionMachine = FunctionMachine.create(new DetachedShuntingYardTransducer<>(
                        MathElement.EXPRESSION, FunctionHolder::setArgument, elementResolverFactory), FunctionHolder::setFunctionName,
                errorMessage -> {throw new ResolvingException(errorMessage);});

        if (functionMachine.run(inputChain, holder)) {

            return Optional.ofNullable(functionFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }

}
