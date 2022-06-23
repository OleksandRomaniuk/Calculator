package src.calculator.impl;

import com.google.common.base.Preconditions;
import src.fsm.Transducer;
import src.fsm.Input;
import src.calculator.impl.fsm.util.ResolvingException;
import src.calculator.impl.math.MathElementResolver;

import java.util.Optional;
import java.util.function.BiConsumer;

/**
 *{@code ShuntingYardTransducer} is a universal implementation of {@link Transducer}
 *  * that can be used for state machines which work based on new instances of ShuntingYardStack}.
 *
 */


public class ShuntingYardTransducer<O> implements Transducer<O> {

    private final MathElementResolver resolver;

    private final BiConsumer<O,Double> resultConsumer;

    public ShuntingYardTransducer(MathElementResolver resolver , BiConsumer<O, Double> resultConsumer) {
        this.resolver = Preconditions.checkNotNull(resolver);
        this.resultConsumer = Preconditions.checkNotNull(resultConsumer);
    }

    @Override
    public boolean doTransition(Input inputChain, O outputChain) throws ResolvingException {
        Optional<Double> resolve = resolver.resolve(inputChain);
        resolve.ifPresent(value -> resultConsumer.accept(outputChain, value));

        return resolve.isPresent();
    }
}
