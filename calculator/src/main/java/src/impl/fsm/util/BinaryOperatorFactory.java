package src.impl.fsm.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 *
 *  {@code BinaryOperatorFactory create an instance of {@link PrioritizedOperator} by symbol.
 */

public class BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(BinaryOperatorFactory.class);

    private final Map<Character, PrioritizedOperator> binaryOperators = new HashMap<>();

    public BinaryOperatorFactory() {

        binaryOperators.put('+', new PrioritizedOperator(PrioritizedOperator.Priority.LOW, Double::sum));
        binaryOperators.put('-', new PrioritizedOperator(PrioritizedOperator.Priority.LOW, (left, right) -> left - right));
        binaryOperators.put('*', new PrioritizedOperator(PrioritizedOperator.Priority.MEDIUM, (left, right) -> left * right));
        binaryOperators.put('/', new PrioritizedOperator(PrioritizedOperator.Priority.MEDIUM, (left, right) -> left / right));
        binaryOperators.put('^', new PrioritizedOperator(PrioritizedOperator.Priority.HIGH, Math::pow));
    }

    public Optional<PrioritizedOperator> create(char operatorSymbol) {

        if (logger.isInfoEnabled()) {

            logger.info("Current binary operator -> {}", operatorSymbol);
        }

        return Optional.ofNullable(binaryOperators.get(operatorSymbol));
    }
}
