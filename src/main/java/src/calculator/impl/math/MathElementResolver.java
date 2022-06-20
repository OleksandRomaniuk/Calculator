package src.calculator.impl.math;

import src.fsm.Input;
import src.calculator.impl.fsm.util.ResolvingException;

import java.util.Optional;

@FunctionalInterface
public interface MathElementResolver {

    Optional<Double> resolve(Input inputChain) throws ResolvingException;
}
