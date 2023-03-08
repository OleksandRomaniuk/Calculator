package src.operators;

import com.google.common.base.Preconditions;
import src.type.Value;

import java.util.function.BiFunction;

/**
 * Abstract class which is {@link Comparable} and have a priority
 * for operations with different data
 */

public abstract class AbstractBinaryOperator implements BiFunction<Value, Value, Value>, Comparable<AbstractBinaryOperator> {

    private final Priority priority;

    public AbstractBinaryOperator(Priority priority) {

        this.priority = Preconditions.checkNotNull(priority);
    }

    @Override
    public int compareTo(AbstractBinaryOperator o) {

        return priority.compareTo(o.priority);
    }

    public enum Priority {
        LOW, MEDIUM, HIGH
    }

}
