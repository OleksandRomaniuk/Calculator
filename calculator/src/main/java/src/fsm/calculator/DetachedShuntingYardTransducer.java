package src.fsm.calculator;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.ResolvingException;
import fsm.Transducer;
import src.math.MathElement;
import src.math.MathElementResolver;
import src.math.MathElementResolverFactory;

import java.util.Optional;
import java.util.function.BiConsumer;

public class DetachedShuntingYardTransducer<O> implements Transducer<O> {

    private final MathElementResolverFactory factory;

    private final MathElement resolverType;

    private final BiConsumer<O, Double> resultConsumer;

    public DetachedShuntingYardTransducer(MathElement resolver, BiConsumer<O, Double> resultConsumer,
                                          MathElementResolverFactory factory) {
        this.resolverType =  Preconditions.checkNotNull(resolver);
        this.resultConsumer = Preconditions.checkNotNull(resultConsumer);
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ResolvingException {

        MathElementResolver resolver = factory.create(resolverType);

        Optional<Double> resolveResult = resolver.resolve(inputChain);

        resolveResult.ifPresent((Double value) -> {
            resultConsumer.accept(outputChain, value);
        });

        return resolveResult.isPresent();
    }
}
