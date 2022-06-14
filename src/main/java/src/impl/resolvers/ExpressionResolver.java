package src.impl.resolvers;

import com.google.common.base.Preconditions;
import src.impl.fsm.expression.ExpressionMachine;
import src.impl.fsm.util.Input;
import src.impl.fsm.util.ResolvingException;
import src.impl.fsm.util.ShuntingYardStack;
import src.impl.math.MathElementResolver;
import src.impl.math.MathElementResolverFactory;

import java.util.Optional;

public class ExpressionResolver implements MathElementResolver {

    private final MathElementResolverFactory factory;

    public ExpressionResolver(MathElementResolverFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {
        ShuntingYardStack nestingShuntingYardStack = new ShuntingYardStack();

        ExpressionMachine expressionMachine = ExpressionMachine.create(factory);

        if (expressionMachine.run(inputChain, nestingShuntingYardStack)) {

            return Optional.of(nestingShuntingYardStack.peekResult());
        }

        return Optional.empty();
    }
}
