package src.calculator.impl;

import com.google.common.base.Preconditions;

/**
 * Class for calculated result
 */

public class Result {

    private final double result;

    Result(double result) {
        Preconditions.checkNotNull(result);

        this.result = result;
    }

    public double getResult() {
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}