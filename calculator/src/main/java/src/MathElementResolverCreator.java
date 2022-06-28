package src;


import src.math.MathElementResolver;

@FunctionalInterface
public interface MathElementResolverCreator {

    MathElementResolver create();
}
