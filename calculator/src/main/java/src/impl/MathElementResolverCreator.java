package src.impl;


import src.impl.math.MathElementResolver;

@FunctionalInterface
public interface MathElementResolverCreator {

    MathElementResolver create();
}
