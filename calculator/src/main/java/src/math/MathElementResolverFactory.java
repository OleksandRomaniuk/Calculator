package src.math;



@FunctionalInterface
public interface MathElementResolverFactory {

    MathElementResolver create(MathElement mathElement);
}
