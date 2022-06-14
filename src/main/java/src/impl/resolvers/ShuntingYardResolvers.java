package src.impl.resolvers;

import com.google.common.base.Preconditions;
import src.impl.fsm.FiniteStateMachine;
import src.impl.fsm.util.Input;
import src.impl.fsm.util.ResolvingException;
import src.impl.fsm.util.ShuntingYardStack;
import src.impl.math.MathElementResolver;

import java.util.Optional;

public class ShuntingYardResolvers<I> implements MathElementResolver {

    private final FiniteStateMachine<I, ShuntingYardStack> machine;

    public ShuntingYardResolvers(FiniteStateMachine<I, ShuntingYardStack> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }


    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {

        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
