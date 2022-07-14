package src.fsm.calculator;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.ResolvingException;
import src.Transducer;
import src.math.MathElement;
import src.math.MathElementResolver;
import src.math.MathElementResolverFactory;
import src.type.Value;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 * {@code DetachedShuntingYardTransducer} is a universal implementation of {@link Transducer}
 * that can be used for state machines which work based on new instances of {@link com.teamdev.implementations.datastructures.ShuntingYard}.
 */

public class DetachedShuntingYardTransducer<O> implements Transducer<O, ResolvingException> {

    private final MathElementResolverFactory factory;

    private final MathElement resolverType;

    private final BiConsumer<O, Value> resultConsumer;

    public DetachedShuntingYardTransducer(MathElement resolver, BiConsumer<O, Value> resultConsumer,
                                          MathElementResolverFactory factory) {
        this.resolverType =  Preconditions.checkNotNull(resolver);
        this.resultConsumer = Preconditions.checkNotNull(resultConsumer);
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ResolvingException {

        MathElementResolver resolver = factory.create(resolverType);

        Optional<Value> resolveResult = resolver.resolve(inputChain);

        resolveResult.ifPresent((Value value) -> resultConsumer.accept(outputChain, value));

        return resolveResult.isPresent();
    }
}
