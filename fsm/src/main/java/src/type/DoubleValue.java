package src.type;

public class DoubleValue implements Value{

    private final double value;

    public DoubleValue(double value) {
        this.value = value;
    }

    @Override
    public void accept(ValueVisitor visitor) {

        visitor.visit(this);

    }

    public double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }
}
