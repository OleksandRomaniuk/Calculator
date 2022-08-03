package src.calucator.resolvers;


import src.CharSequenceReader;

import src.Transducer;
import src.calucator.fsm.calculator.DetachedShuntingYardTransducer;
import src.calucator.fsm.ResolvingException;
import src.calucator.fsm.function.FunctionFactory;
import src.calucator.fsm.function.FunctionHolder;
import src.calucator.fsm.function.FunctionMachine;
import src.calucator.math.MathElement;
import src.calucator.math.MathElementResolver;
import src.calucator.math.MathElementResolverFactory;
import src.type.Value;

import java.util.Optional;

/**
 * Implementation of MathElementResolver that
 * resolve input chain for unctionMachine.
 */

public class FunctionResolver implements MathElementResolver {

    private final MathElementResolverFactory elementResolverFactory;

    private final FunctionFactory functionFactory = new FunctionFactory();

    public FunctionResolver(MathElementResolverFactory factory) {

        this.elementResolverFactory = factory;
    }

    @Override
    public Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException {

        FunctionHolder holder = new FunctionHolder();

        var functionMachine = FunctionMachine.create(Transducer.checkAndPassChar('('), FunctionHolder::setFunctionName, new DetachedShuntingYardTransducer<>(
                        MathElement.EXPRESSION, FunctionHolder::setArgument, elementResolverFactory),
                Transducer.checkAndPassChar(')'),
                errorMessage -> {throw new ResolvingException(errorMessage);});

        if (functionMachine.run(inputChain, holder)) {

            return Optional.ofNullable(functionFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }

}
