package src;

import java.util.Optional;

public interface BinaryOperatorFactory {

    Optional<AbstractBinaryOperator> create(String operatorSign);
}
