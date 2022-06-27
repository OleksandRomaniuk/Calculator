package src.resolvers;

import com.google.common.base.Preconditions;
import src.fsm.Input;
import src.ProgramMemory;
import src.impl.fsm.expression.ExpressionMachine;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;


import java.util.Optional;

public class variableExpressionResolver implements MathElementResolver {

    private final MathElementResolverFactory factory;

    public variableExpressionResolver(MathElementResolverFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {
        ProgramMemory nestingShuntingYardStack = new ProgramMemory();

        ExpressionMachine expressionMachine = ExpressionMachine.create(factory);

        if (expressionMachine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
