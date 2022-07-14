package src.operators;

import java.util.Optional;

public interface BinaryOperatorFactory {

    Optional<AbstractBinaryOperator> create(String operatorSign);
}
