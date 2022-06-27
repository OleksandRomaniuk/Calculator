package src.resolvers;


import src.fsm.Input;
import src.ProgramMemory;
import src.impl.fsm.function.FunctionFactory;
import src.impl.fsm.function.FunctionMachine;
import src.impl.fsm.util.FunctionHolder;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;

import java.util.Optional;

public class FunctionResolver implements MathElementResolver {

    private final MathElementResolverFactory elementResolverFactory;

    private final FunctionFactory procedureFactory = new FunctionFactory();

    public FunctionResolver(MathElementResolverFactory factory) {

        this.elementResolverFactory = factory;
    }

    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {

        FunctionHolder holder = new FunctionHolder();

        FunctionMachine functionMachine = FunctionMachine.create(elementResolverFactory);

        if (functionMachine.run(inputChain, holder)) {

            ProgramMemory output = new ProgramMemory();

            return Optional.ofNullable(procedureFactory.create(holder.getFunctionName())
                    .evaluate(holder.getArguments()));
        }

        return Optional.empty();
    }
}
