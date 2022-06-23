package src.calculator.impl.resolvers;

import com.google.common.base.Preconditions;
import src.calculator.impl.fsm.expression.ExpressionMachine;
import src.fsm.Input;
import src.calculator.impl.fsm.util.ResolvingException;
import src.calculator.impl.fsm.util.ShuntingYard;
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
        ShuntingYard nestingShuntingYard = new ShuntingYard();

        ExpressionMachine expressionMachine = ExpressionMachine.create(factory);

        if (expressionMachine.run(inputChain, nestingShuntingYard)) {

            return Optional.of(nestingShuntingYard.peekResult());
        }

        return Optional.empty();
    }
}
