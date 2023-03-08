package src.type;

/**
 * Implementation of {@link Value} that define string type
 */

public class StringValue implements Value {

    private final String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public void accept(ValueVisitor visitor) {

        visitor.visit(this);
    }

    String getStringValue() {
        return value;
    }

    @Override
    public String toString() {
        return getStringValue();
    }
}
