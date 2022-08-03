package src.operators;





import src.type.DoubleValue;
import src.type.DoubleValueVisitor;
import src.type.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

/**
 * {@code PrefixUnaryOperatorFactory} is an implementation of {@link UnaryOperatorFactory}
 * that used to create an instance of {@link UnaryOperator} by symbol.
 */

public class PrefixUnaryOperatorFactory implements UnaryOperatorFactory {

    private final Map<String, UnaryOperator<Value>> prefixUnaryOperators = new HashMap<>();

    public PrefixUnaryOperatorFactory() {

        prefixUnaryOperators.put("++", value -> new DoubleValue(DoubleValueVisitor.read(value) + 1));

        prefixUnaryOperators.put("--", value -> new DoubleValue(DoubleValueVisitor.read(value) - 1));
    }

    @Override
    public Optional<UnaryOperator<Value>> create(String unaryOperator) {

        return Optional.ofNullable(prefixUnaryOperators.get(unaryOperator));
    }

    @Override
    public Set<String> getOperators() {
        return prefixUnaryOperators.keySet();
    }
}
