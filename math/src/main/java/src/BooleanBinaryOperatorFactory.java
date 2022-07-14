package src;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class BooleanBinaryOperatorFactory implements BinaryOperatorFactory {

    private final Map<String, AbstractBinaryOperator> logicalOperators = new HashMap<>();

    public BooleanBinaryOperatorFactory() {

        logicalOperators.put("&&", new BooleanBinaryOperator(AbstractBinaryOperator.Priority.MEDIUM,
                (left, right) -> left && right));

        logicalOperators.put("||", new BooleanBinaryOperator(AbstractBinaryOperator.Priority.LOW,
                (left, right) -> left || right));
    }

    @Override
    public Optional<AbstractBinaryOperator> create(String operatorSign) {

        return Optional.ofNullable(logicalOperators.get(operatorSign));
    }
}
