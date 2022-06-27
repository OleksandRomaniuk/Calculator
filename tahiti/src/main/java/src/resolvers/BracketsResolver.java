package src.resolvers;



import src.fsm.Input;
import src.ProgramMemory;
import src.impl.fsm.brackets.BracketsMachine;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;

import java.util.Optional;

public class BracketsResolver implements MathElementResolver {

    private final MathElementResolverFactory factory;

    public BracketsResolver(MathElementResolverFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {

        ProgramMemory nestingShuntingYardStack = new ProgramMemory();

        BracketsMachine machine = BracketsMachine.create(factory);

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
