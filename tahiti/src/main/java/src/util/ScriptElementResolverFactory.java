package src.util;



import static src.impl.math.MathElement.*;


public class ScriptElementResolverFactory implements MathElementResolverFactory {

    private final Map<MathElement, ResolverFactoryImpl.MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    public ScriptElementResolverFactory() {
        resolvers.put(NUMBER, ScriptNumberResolver::new);

        resolvers.put(EXPRESSION, () -> new variableExpressionResolver(this));

        resolvers.put(OPERAND, () -> new DetachedMemoryResolver<>(OperandMachine.create(this)));

        resolvers.put(BRACKETS, () -> new BracketsResolver(this));

        resolvers.put(FUNCTION, () -> new FunctionResolver(this));
    }

    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        MathElementResolverCreator resolverCreator = resolvers.get(mathElement);

        return resolverCreator.create();
    }
}
