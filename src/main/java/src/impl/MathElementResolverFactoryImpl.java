package src.impl;

import com.google.common.base.Preconditions;
import src.impl.fsm.brackets.BracketsMachine;
import src.impl.fsm.operand.OperandMachine;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;
import src.impl.resolvers.ShuntingYardResolvers;
import src.impl.resolvers.ExpressionResolver;
import src.impl.resolvers.FunctionResolver;
import src.impl.resolvers.NumberResolver;

import java.util.EnumMap;
import java.util.Map;

import static src.impl.math.MathElement.*;

public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    MathElementResolverFactoryImpl() {

        resolvers.put(NUMBER, NumberResolver::new);

        resolvers.put(EXPRESSION, () -> new ExpressionResolver(this));

        resolvers.put(FUNCTION, () -> new FunctionResolver(this));

        resolvers.put(OPERAND, () -> new ShuntingYardResolvers<>(OperandMachine.create(this)));

        resolvers.put(BRACKETS, () -> new ShuntingYardResolvers<>(BracketsMachine.create(this)));

    }


    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        MathElementResolverCreator resolverCreator = resolvers.get(mathElement);

        return resolverCreator.create();
    }

    @FunctionalInterface
    interface MathElementResolverCreator{

        MathElementResolver create();
    }
}
