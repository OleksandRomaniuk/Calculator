package src.type;

import java.util.Optional;

/**
 * Implementation of {@link ValueVisitor} that define opportunity of read and use boolean value.
 */

public class BooleanValueVisitor implements ValueVisitor {

    private final boolean throwExceptionPermission;
    private Boolean booleanValue;

    private BooleanValueVisitor(boolean throwExceptionPermission) {
        this.throwExceptionPermission = throwExceptionPermission;
    }

    public static Boolean read(Value value) {
        BooleanValueVisitor booleanValueVisitor = new BooleanValueVisitor(true);

        value.accept(booleanValueVisitor);

        return booleanValueVisitor.getBooleanValue();
    }

    public static Boolean isBoolean(Value value) {

        BooleanValueVisitor visitor = new BooleanValueVisitor(false);

        value.accept(visitor);

        return Optional.ofNullable(visitor.getBooleanValue()).isPresent();

    }

    @Override
    public void visit(DoubleValue value) {
        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch: expected boolean but double provided");
        }
    }

    @Override
    public void visit(BooleanValue value) {

        booleanValue = value.getBooleanValue();
    }

    @Override
    public void visit(StringValue value) {
        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch: expected boolean but string provided");
        }
    }

    private Boolean getBooleanValue() {
        return booleanValue;
    }
}
