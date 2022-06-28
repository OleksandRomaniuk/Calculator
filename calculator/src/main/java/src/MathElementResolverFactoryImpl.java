package src;

import com.google.common.base.Preconditions;
import fsm.FiniteStateMachine;

import src.fsm.brackets.BracketsMachine;
import src.fsm.calculator.DetachedShuntingYardTransducer;
import src.fsm.expression.ExpressionMachine;
import src.math.MathElement;
import src.math.MathElementResolver;
import src.math.MathElementResolverFactory;
import src.resolvers.DetachedShuntingYardResolver;
import src.resolvers.FunctionResolver;
import src.resolvers.NumberResolver;


import java.util.EnumMap;
import java.util.Map;


public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    MathElementResolverFactoryImpl() {

        resolvers.put(MathElement.NUMBER, NumberResolver::new);

        resolvers.put(MathElement.EXPRESSION, () -> new DetachedShuntingYardResolver<>
                (ExpressionMachine.create(ShuntingYard::pushOperator,
                        new DetachedShuntingYardTransducer<>(MathElement.OPERAND, ShuntingYard::pushOperand, this))));

        resolvers.put(MathElement.OPERAND, () -> new DetachedShuntingYardResolver<>(
                FiniteStateMachine.oneOfMachine(new DetachedShuntingYardTransducer<>(MathElement.NUMBER, ShuntingYard::pushOperand, this),
                        new DetachedShuntingYardTransducer<>(MathElement.BRACKETS, ShuntingYard::pushOperand, this),
                        new DetachedShuntingYardTransducer<>(MathElement.FUNCTION, ShuntingYard::pushOperand, this))));

        resolvers.put(MathElement.BRACKETS, () -> new DetachedShuntingYardResolver<>(BracketsMachine.create(
                new DetachedShuntingYardTransducer<>(MathElement.EXPRESSION, ShuntingYard::pushOperand, this))));

        resolvers.put(MathElement.FUNCTION, () -> new FunctionResolver(this));
    }


    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        MathElementResolverCreator resolverCreator = resolvers.get(mathElement);

        return resolverCreator.create();
    }

}
