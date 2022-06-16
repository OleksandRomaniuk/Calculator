package src.calculator.impl.resolvers;

import com.google.common.base.Preconditions;
import src.calculator.impl.fsm.FiniteStateMachine;
import src.calculator.impl.fsm.util.Input;
import src.calculator.impl.fsm.util.ResolvingException;
import src.calculator.impl.fsm.util.ShuntingYardStack;
import src.calculator.impl.math.MathElementResolver;

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
