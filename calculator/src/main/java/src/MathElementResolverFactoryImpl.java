package src;

import com.google.common.base.Preconditions;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import src.fsm.ShuntingYard;
import src.fsm.calculator.DetachedShuntingYardTransducer;
import src.fsm.expression.ExpressionMachine;
import src.math.MathElement;
import src.math.MathElementResolver;
import src.math.MathElementResolverFactory;
import src.resolvers.DetachedShuntingYardResolver;
import src.resolvers.FunctionResolver;
import src.resolvers.NumberResolver;


import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import static src.math.MathElement.*;


public class MathElementResolverFactoryImpl implements MathElementResolverFactory {

    private final Map<MathElement, MathElementResolverCreator> resolvers = new EnumMap<>(MathElement.class);

    MathElementResolverFactoryImpl() {

        resolvers.put(NUMBER, NumberResolver::new);

        resolvers.put(EXPRESSION, () -> new DetachedShuntingYardResolver<>
                (ExpressionMachine.create(ShuntingYard::pushOperator,
                        new DetachedShuntingYardTransducer<>(OPERAND, ShuntingYard::pushOperand, this),
                        errorMessage -> {
                            throw new ResolvingException(errorMessage);
                        })));

        resolvers.put(OPERAND, () -> new DetachedShuntingYardResolver<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ResolvingException(errorMessage);
                        },
                        new DetachedShuntingYardTransducer<>(NUMBER, ShuntingYard::pushOperand, this).named("Number"),
                        new DetachedShuntingYardTransducer<>(MathElement.BRACKETS, ShuntingYard::pushOperand, this).named("Brackets"),
                        new DetachedShuntingYardTransducer<>(MathElement.FUNCTION, ShuntingYard::pushOperand, this).named("Function"))));



        resolvers.put(BRACKETS, () -> new DetachedShuntingYardResolver<>(
                FiniteStateMachine.chainMachine(errorMessage -> {
                            throw new ResolvingException(errorMessage);
                        }, List.of(), List.of(Transducer.checkAndPassChar('('),
                        new DetachedShuntingYardTransducer<>(EXPRESSION, ShuntingYard::pushOperand, this).named("Expression"),
                        Transducer.checkAndPassChar(')'))

                )
        ));

        resolvers.put(FUNCTION, () -> new FunctionResolver(this));
    }


    @Override
    public MathElementResolver create(MathElement mathElement) {
        Preconditions.checkState(resolvers.containsKey(Preconditions.checkNotNull(mathElement)));

        var resolverCreator = resolvers.get(mathElement);

        return resolverCreator.create();
    }

}