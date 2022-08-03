package src.calucator.fsm.calculator;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.calucator.fsm.ResolvingException;
import src.calucator.math.MathElement;
import src.calucator.math.MathElementResolverFactory;
import src.type.Value;

import java.util.function.BiConsumer;

/**
 * Implementation of {@link Transducer} that can be used for state machines which work based on ShuntingYard
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

        var resolver = factory.create(resolverType);

       var resolveResult = resolver.resolve(inputChain);

        resolveResult.ifPresent((Value value) -> resultConsumer.accept(outputChain, value));

        return resolveResult.isPresent();
    }
}
