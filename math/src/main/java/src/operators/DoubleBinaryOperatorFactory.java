package src.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

/**
 * Implementation of {@link BinaryOperatorFactory} to create an {@link DoubleBinaryOperator}.
 */

public class DoubleBinaryOperatorFactory implements BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(DoubleBinaryOperatorFactory.class);

    private final Map<String, AbstractBinaryOperator> binaryOperators = new HashMap<>();

    public DoubleBinaryOperatorFactory() {

        binaryOperators.put("+", new DoubleBinaryOperator(AbstractBinaryOperator.Priority.LOW, Double::sum));
        binaryOperators.put("-", new DoubleBinaryOperator(AbstractBinaryOperator.Priority.LOW, (left, right) -> left - right));
        binaryOperators.put("*", new DoubleBinaryOperator(AbstractBinaryOperator.Priority.MEDIUM, (left, right) -> left * right));
        binaryOperators.put("/", new DoubleBinaryOperator(AbstractBinaryOperator.Priority.MEDIUM, (left, right) -> left / right));
        binaryOperators.put("^", new DoubleBinaryOperator(AbstractBinaryOperator.Priority.HIGH, Math::pow));
    }

    @Override
    public Optional<AbstractBinaryOperator> create(String operatorSign) {

        if (binaryOperators.containsKey(operatorSign)) {
            if (logger.isInfoEnabled()) {

                logger.info("Current binary operator -> {}", operatorSign);
            }
        }

        return Optional.ofNullable(binaryOperators.get(operatorSign));
    }

    @Override
    public Set<String> getOperators() {
        return binaryOperators.keySet();
    }
}
