package src.calculator.impl.resolvers;

import com.google.common.base.Preconditions;
import src.calculator.impl.fsm.expression.ExpressionMachine;
import src.fsm.Input;
import src.calculator.impl.fsm.util.ResolvingException;
import src.calculator.impl.fsm.util.ShuntingYardStack;
import src.calculator.impl.math.MathElementResolver;
import src.calculator.impl.math.MathElementResolverFactory;

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
