package src.calculator.impl;

import com.google.common.base.Preconditions;
import src.calculator.impl.fsm.brackets.BracketsMachine;
import src.calculator.impl.fsm.operand.OperandMachine;
import src.calculator.impl.math.MathElement;
import src.calculator.impl.math.MathElementResolver;
import src.calculator.impl.math.MathElementResolverFactory;
import src.calculator.impl.resolvers.ShuntingYardResolvers;
import src.calculator.impl.resolvers.ExpressionResolver;
import src.calculator.impl.resolvers.FunctionResolver;
import src.calculator.impl.resolvers.NumberResolver;

import java.util.EnumMap;
import java.util.Map;

import static src.calculator.impl.math.MathElement.*;

/**
 * Implementation of {@link MathElementResolverFactory} interface
 */

public class ResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    ResolverFactoryImpl() {

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
