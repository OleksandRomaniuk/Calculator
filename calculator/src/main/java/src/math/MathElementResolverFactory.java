package src.math;

/**
 * {@code MathElementResolverFactory} is a functional interface that
 * represents a factory pattern for creating {@link MathElementResolver}.
 */

@FunctionalInterface
public interface MathElementResolverFactory {

    MathElementResolver create(MathElement mathElement);
}
