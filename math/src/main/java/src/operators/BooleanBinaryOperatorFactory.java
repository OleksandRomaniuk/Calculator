package src.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class BooleanBinaryOperatorFactory implements BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(BooleanBinaryOperatorFactory.class);

    private final Map<String, AbstractBinaryOperator> logicalOperators = new HashMap<>();

    public BooleanBinaryOperatorFactory() {

        logicalOperators.put("&&", new BooleanBinaryOperator(AbstractBinaryOperator.Priority.MEDIUM,
                (left, right) -> left && right));

        logicalOperators.put("||", new BooleanBinaryOperator(AbstractBinaryOperator.Priority.LOW,
                (left, right) -> left || right));
    }

    @Override
    public Optional<AbstractBinaryOperator> create(String operatorSign) {

        if (logicalOperators.containsKey(operatorSign)) {
            if (logger.isInfoEnabled()) {

                logger.info("Current logical operator -> {}", operatorSign);
            }
        }

        return Optional.ofNullable(logicalOperators.get(operatorSign));
    }

    @Override
    public Set<String> getOperators() {
        return logicalOperators.keySet();
    }
}
