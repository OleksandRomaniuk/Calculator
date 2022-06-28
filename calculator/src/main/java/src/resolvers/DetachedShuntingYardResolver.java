package src.resolvers;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import fsm.ResolvingException;
import src.ShuntingYard;
import src.math.MathElementResolver;


import java.util.Optional;



public class DetachedShuntingYardResolver<I> implements MathElementResolver {

    private final FiniteStateMachine<I, ShuntingYard> machine;

    public DetachedShuntingYardResolver(FiniteStateMachine<I, ShuntingYard> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }


    @Override
    public Optional<Double> resolve(CharSequenceReader inputChain) throws ResolvingException {

        ShuntingYard nestingShuntingYardStack = new ShuntingYard();

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
