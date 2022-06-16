package src.impl.fsm.util;

import com.google.common.base.Preconditions;

import java.util.function.DoubleBinaryOperator;

/**
 *Class for setting and comparing the priority of functions
 */

public class PrioritizedOperator implements DoubleBinaryOperator, Comparable<PrioritizedOperator> {


    public enum Priority {
        LOW,
        MEDIUM,
        HIGH
    }

    private final DoubleBinaryOperator origin;

    private final Priority priority;

    @Override
    public int compareTo(PrioritizedOperator o) {

        return priority.compareTo(o.priority);
    }

    public PrioritizedOperator(Priority priority, DoubleBinaryOperator origin) {

        this.priority = Preconditions.checkNotNull(priority);
        this.origin = Preconditions.checkNotNull(origin);
    }

    @Override
    public double applyAsDouble(double left, double right) {


        return origin.applyAsDouble(left, right);
    }

}
