package src;

/**
 * {@code CalculationResult} is a tiny type which is used to store result of calculation.
 */

public class CalculationResult {

    private final double result;

    CalculationResult(double result) {

        this.result = result;
    }

    public double getResult() {
        return result;
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

}
