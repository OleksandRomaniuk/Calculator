package src.type;

public class BooleanValue implements Value{

    private final boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public void accept(ValueVisitor visitor) {
        visitor.visit(this);
    }

    public boolean getBooleanValue() {
        return value;
    }

    @Override
    public String toString() {
        return Boolean.toString(value);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        BooleanValue that = (BooleanValue) o;

        return value == that.value;
    }
}
