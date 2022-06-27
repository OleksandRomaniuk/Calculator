package src.impl.fsm.util;

import com.google.common.base.Preconditions;

import java.util.function.DoubleBinaryOperator;

/**
 * {@code PrioritizedBinaryOperator} is an implementation of {@link DoubleBinaryOperator}
 * which is {@link Comparable} and have a priority.
 * <p>
 * Note: this class has a natural ordering that is inconsistent with equals.
 * </p>
 */

public class PrioritizedBinaryOperator implements DoubleBinaryOperator, Comparable<PrioritizedBinaryOperator> {


    public enum Priority {
        LOW, MEDIUM, HIGH
    }

    private final DoubleBinaryOperator origin;

    private final Priority priority;

    @Override
    public int compareTo(PrioritizedBinaryOperator o) {

        return priority.compareTo(o.priority);
    }

    public PrioritizedBinaryOperator(Priority priority, DoubleBinaryOperator origin) {

        this.priority = Preconditions.checkNotNull(priority);
        this.origin = Preconditions.checkNotNull(origin);
    }

    @Override
    public double applyAsDouble(double left, double right) {


        return origin.applyAsDouble(left, right);
    }

}
