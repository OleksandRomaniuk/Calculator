package src.operators;



import src.type.Value;

import java.util.Optional;
import java.util.Set;
import java.util.function.UnaryOperator;

/**
 * {@code UnaryOperatorFactory} is an interface for realisation of factory pattern
 * that create an {@link UnaryOperator}.
 */

public interface UnaryOperatorFactory {

    Optional<UnaryOperator<Value>> create(String unaryOperator);

    Set<String> getOperators();

}
