package src;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * {@code BinaryOperatorFactory} is a realization of factory pattern
 * that create an instance of {@link AbstractBinaryOperator} by symbol.
 */

public class DoubleBinaryOperatorFactory implements BinaryOperatorFactory{

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
}
