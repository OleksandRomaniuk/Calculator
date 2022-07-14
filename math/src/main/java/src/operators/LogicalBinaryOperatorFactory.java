package src.operators;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LogicalBinaryOperatorFactory implements BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(LogicalBinaryOperatorFactory.class);

    private final Map<String, AbstractBinaryOperator> logicalOperators = new HashMap<>();

    public LogicalBinaryOperatorFactory() {

        logicalOperators.put("&&", new LogicalBinaryOperator(AbstractBinaryOperator.Priority.MEDIUM,
                (left, right) -> left && right));

        logicalOperators.put("||", new LogicalBinaryOperator(AbstractBinaryOperator.Priority.LOW,
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
}
