package src.program;

import com.google.common.base.Preconditions;

/**
 * Class for program result
 */

public class ProgramResult {

    private final double result;

    ProgramResult(double result) {
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
