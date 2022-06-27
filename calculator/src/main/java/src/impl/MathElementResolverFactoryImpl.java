package src.impl;

import com.google.common.base.Preconditions;
import fsm.FiniteStateMachine;
import src.impl.fsm.brackets.BracketsMachine;
import src.impl.fsm.calculator.DetachedShuntingYardTransducer;
import src.impl.fsm.expression.ExpressionMachine;
import src.impl.fsm.util.ShuntingYard;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;
import src.impl.resolvers.DetachedShuntingYardResolver;
import src.impl.resolvers.FunctionResolver;
import src.impl.resolvers.NumberResolver;


import java.util.EnumMap;
import java.util.Map;

import static src.impl.math.MathElement.*;


public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    MathElementResolverFactoryImpl() {

        resolvers.put(NUMBER, NumberResolver::new);

        resolvers.put(EXPRESSION, () -> new DetachedShuntingYardResolver<>
                (ExpressionMachine.create(ShuntingYard::pushOperator,
                        new DetachedShuntingYardTransducer<>(OPERAND, ShuntingYard::pushOperand, this))));

        resolvers.put(OPERAND, () -> new DetachedShuntingYardResolver<>(
                FiniteStateMachine.oneOfMachine(new DetachedShuntingYardTransducer<>(MathElement.NUMBER, ShuntingYard::pushOperand, this),
                        new DetachedShuntingYardTransducer<>(MathElement.BRACKETS, ShuntingYard::pushOperand, this),
                        new DetachedShuntingYardTransducer<>(FUNCTION, ShuntingYard::pushOperand, this))));

        resolvers.put(BRACKETS, () -> new DetachedShuntingYardResolver<>(BracketsMachine.create(
                new DetachedShuntingYardTransducer<>(EXPRESSION, ShuntingYard::pushOperand, this))));

        resolvers.put(FUNCTION, () -> new FunctionResolver(this));
    }


    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        MathElementResolverCreator resolverCreator = resolvers.get(mathElement);

        return resolverCreator.create();
    }

}
