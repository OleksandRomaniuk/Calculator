package src.calucator.math;

/**
 * Functional interface that represents a factory pattern for creating  MathElementResolver.
 */

@FunctionalInterface
public interface MathElementResolverFactory {

    MathElementResolver create(MathElement mathElement);
}
