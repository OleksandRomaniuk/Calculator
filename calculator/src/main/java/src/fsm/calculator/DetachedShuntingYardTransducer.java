package src.fsm.calculator;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.Transducer;
import src.ResolvingException;
import src.math.MathElement;
import src.math.MathElementResolverFactory;
import fsm.type.Value;

import java.util.function.BiConsumer;

/**
 * DetachedShuntingYardTransducer is a universal implementation of  Transducer
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
