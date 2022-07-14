package src;

import com.google.common.base.Preconditions;
import src.type.Value;

import java.util.function.BiFunction;
import java.util.function.DoubleBinaryOperator;

/**
 * {@code PrioritizedBinaryOperator} is an implementation of {@link DoubleBinaryOperator}
 * which is {@link Comparable} and have a priority.
 * <p>
 * Note: this class has a natural ordering that is inconsistent with equals.
 * </p>
 */

public abstract class AbstractBinaryOperator implements BiFunction<Value, Value, Value>, Comparable<AbstractBinaryOperator> {


    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    private final Priority priority;

    @Override
    public int compareTo(AbstractBinaryOperator o) {

        return priority.compareTo(o.priority);
    }

    public AbstractBinaryOperator(Priority priority) {

        this.priority = Preconditions.checkNotNull(priority);
    }


}
