package src.fsm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


public class BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(BinaryOperatorFactory.class);

    private final Map<Character, PrioritizedBinaryOperator> binaryOperators = new HashMap<>();

    public BinaryOperatorFactory() {

        binaryOperators.put('+', new PrioritizedBinaryOperator(PrioritizedBinaryOperator.Priority.LOW, Double::sum));
        binaryOperators.put('-', new PrioritizedBinaryOperator(PrioritizedBinaryOperator.Priority.LOW, (left, right) -> left - right));
        binaryOperators.put('*', new PrioritizedBinaryOperator(PrioritizedBinaryOperator.Priority.MEDIUM, (left, right) -> left * right));
        binaryOperators.put('/', new PrioritizedBinaryOperator(PrioritizedBinaryOperator.Priority.MEDIUM, (left, right) -> left / right));
        binaryOperators.put('^', new PrioritizedBinaryOperator(PrioritizedBinaryOperator.Priority.HIGH, Math::pow));
    }

    public Optional<PrioritizedBinaryOperator> create(char operatorSymbol) {

        if (logger.isInfoEnabled()) {

            logger.info("Current binary operator -> {}", operatorSymbol);
        }

        return Optional.ofNullable(binaryOperators.get(operatorSymbol));
    }
}
