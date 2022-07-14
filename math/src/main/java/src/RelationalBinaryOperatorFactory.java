package src;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static src.AbstractBinaryOperator.Priority.HIGH;


public class RelationalBinaryOperatorFactory implements BinaryOperatorFactory {

    private static final Logger logger = LoggerFactory.getLogger(RelationalBinaryOperatorFactory.class);

    private final Map<String, AbstractBinaryOperator> relationalOperators = new HashMap<>();

    public RelationalBinaryOperatorFactory() {

        relationalOperators.put(">", new RelationalBinaryOperator(HIGH, (left, right) -> left > right));
        relationalOperators.put("<", new RelationalBinaryOperator(HIGH, (left, right) -> left < right));
        relationalOperators.put(">=", new RelationalBinaryOperator(HIGH, (left, right) -> left >= right));
        relationalOperators.put("<=", new RelationalBinaryOperator(HIGH, (left, right) -> left <= right));
    }

    @Override
    public Optional<AbstractBinaryOperator> create(String operatorSign) {


        if (relationalOperators.containsKey(operatorSign)) {
            if (logger.isInfoEnabled()) {

                logger.info("Current relational operator -> {}", operatorSign);
            }
        }

        return Optional.ofNullable(relationalOperators.get(operatorSign));
    }
}
