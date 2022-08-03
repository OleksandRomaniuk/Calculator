package src.type;

import java.util.Optional;

/**
 * Implementation of {@link ValueVisitor} that
 * define opportunity of read and use double value.
 */

public class DoubleValueVisitor implements ValueVisitor {

    private final boolean throwExceptionPermission;
    private Double doubleValue;

    private DoubleValueVisitor(boolean throwExceptionPermission) {
        this.throwExceptionPermission = throwExceptionPermission;
    }

    public static Double read(Value value) {
        DoubleValueVisitor doubleVisitor = new DoubleValueVisitor(true);

        value.accept(doubleVisitor);

        return doubleVisitor.getDoubleValue();
    }

    public static boolean isDouble(Value value) {

        DoubleValueVisitor visitor = new DoubleValueVisitor(false);

        value.accept(visitor);

        Double result = visitor.getDoubleValue();

        return Optional.ofNullable(result).isPresent();
    }

    @Override
    public void visit(DoubleValue value) {

        doubleValue = value.getValue();
    }

    @Override
    public void visit(BooleanValue value) {

        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch: expected double but boolean provided");
        }
    }

    @Override
    public void visit(StringValue value) {
        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch: expected double but String provided");
        }
    }

    private Double getDoubleValue() {
        return doubleValue;
    }
}
