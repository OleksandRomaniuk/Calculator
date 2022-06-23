package src.impl.resolvers;

import com.google.common.base.Preconditions;
import fsm.FiniteStateMachine;
import fsm.Input;
import src.impl.fsm.util.ResolvingException;
import src.impl.fsm.util.ShuntingYard;
import src.impl.math.MathElementResolver;


import java.util.Optional;

public class ShuntingYardResolvers<I> implements MathElementResolver {

    private final FiniteStateMachine<I, ShuntingYard> machine;

    public ShuntingYardResolvers(FiniteStateMachine<I, ShuntingYard> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }


    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {

        ShuntingYard nestingShuntingYard = new ShuntingYard();

        if (machine.run(inputChain, nestingShuntingYard)) {

            return Optional.of(nestingShuntingYard.peekResult());
        }

        return Optional.empty();
    }
}
