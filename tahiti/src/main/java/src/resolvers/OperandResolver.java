package src.resolvers;


import src.fsm.FiniteStateMachine;
import src.fsm.Input;
import src.ProgramMemory;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;
import src.operand.OperandMachine;

import java.util.Optional;

public class OperandResolver implements MathElementResolver {

    private final MathElementResolverFactory factory;

    public OperandResolver(MathElementResolverFactory factory) {
        this.factory = factory;
    }

    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {

        FiniteStateMachine<Object, ProgramMemory> machine = OperandMachine.create(factory);

        ProgramMemory nestingShuntingYardStack = new ProgramMemory();

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
