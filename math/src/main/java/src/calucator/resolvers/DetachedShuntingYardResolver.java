package src.calucator.resolvers;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.FiniteStateMachine;

import src.calucator.fsm.ResolvingException;
import src.calucator.fsm.function.ShuntingYard;
import src.calucator.math.MathElementResolver;
import src.type.Value;

import java.util.Optional;

/**
 * {@code DetachedShuntingYardResolver} is a universal implementation of {@link MathElementResolver}
 * that can be used for resolve input chain for {@link FiniteStateMachine}
 * which work based on new instances of {@link ShuntingYard}.
 * @param <I> input chain for state machine.
 */

public class DetachedShuntingYardResolver<I> implements MathElementResolver {

    private final FiniteStateMachine<I, ShuntingYard, ResolvingException> machine;

    public DetachedShuntingYardResolver(FiniteStateMachine<I, ShuntingYard, ResolvingException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }


    @Override
    public Optional<Value> resolve(CharSequenceReader inputChain) throws ResolvingException {

        ShuntingYard nestingShuntingYardStack = new ShuntingYard();

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.result());
        }

        return Optional.empty();
    }
}
