package src.resolvers;

import com.google.common.base.Preconditions;
import src.fsm.FiniteStateMachine;
import src.fsm.Input;
import src.ProgramMemory;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolver;


import java.util.Optional;

public class DetachedMemoryResolver<I> implements MathElementResolver {

    private final FiniteStateMachine<I, ProgramMemory> machine;

    public DetachedMemoryResolver(FiniteStateMachine<I, ProgramMemory> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }


    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {

        ProgramMemory nestingShuntingYardStack = new ProgramMemory();

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
