package src.type;

/**
 * Implementation of {@link Value} that define double type
 */

public class DoubleValue implements Value {

    private final Double value;

    public DoubleValue(Double value) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DoubleValue that = (DoubleValue) o;

        return Double.compare(that.value, value) == 0;
    }
}
