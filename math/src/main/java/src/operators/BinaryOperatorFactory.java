package src.operators;

import src.operators.AbstractBinaryOperator;

import java.util.Optional;
import java.util.Set;

public interface BinaryOperatorFactory {

    Optional<AbstractBinaryOperator> create(String operatorSign);

    Set<String> getOperators();

}
