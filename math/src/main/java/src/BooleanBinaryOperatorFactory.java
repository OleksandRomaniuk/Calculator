package src;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static src.AbstractBinaryOperator.Priority.LOW;

/**
 * {@BooleanBinaryOperatorFactory} is an implementation of {@BinaryOperatorFactory}
 *  which contains the logical operators && and || for logical types
 *
 */

public class BooleanBinaryOperatorFactory implements BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(BooleanBinaryOperatorFactory.class);

    private final Map<String, AbstractBinaryOperator> booleanOperators = new HashMap<>();

    public BooleanBinaryOperatorFactory() {

        booleanOperators.put("&&", new BooleanBinaryOperator(LOW, (left, right) -> left && right));
        booleanOperators.put("||", new BooleanBinaryOperator(LOW, (left, right) -> left || right));

    }

    @Override
    public Optional<AbstractBinaryOperator> create(String operatorSign) {

        if (booleanOperators.containsKey(operatorSign)) {
            if (logger.isInfoEnabled()) {
                logger.info("Current binary operator -> {}", operatorSign);
            }
        }
        return Optional.ofNullable(booleanOperators.get(operatorSign));
    }
}
