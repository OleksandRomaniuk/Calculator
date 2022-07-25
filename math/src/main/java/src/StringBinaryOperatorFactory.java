package src;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


/**
 * Implementation of {@link BinaryOperatorFactory} to create an {@link StringBinaryOperator}.
 */

public class StringBinaryOperatorFactory implements BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(StringBinaryOperatorFactory.class);

    private final Map<String, AbstractBinaryOperator> stringOperators = new HashMap<>();

    public StringBinaryOperatorFactory() {

        stringOperators.put("+", new StringBinaryOperator(AbstractBinaryOperator.Priority.MEDIUM, (left, right) -> left + right));
    }

    @Override
    public Optional<AbstractBinaryOperator> create(String operatorSign) {

        if (stringOperators.containsKey(operatorSign)) {
            if (logger.isInfoEnabled()) {

                logger.info("Current string operator -> {}", operatorSign);
            }
        }

        return Optional.ofNullable(stringOperators.get(operatorSign));
    }
}
