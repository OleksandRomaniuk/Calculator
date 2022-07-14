package src.resolvers;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import fsm.type.Value;
import src.ResolvingException;
import src.fsm.ShuntingYard;
import src.math.MathElementResolver;

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

        var nestingShuntingYardStack = new ShuntingYard();

        if (machine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.popResult());
        }

        return Optional.empty();
    }
}
