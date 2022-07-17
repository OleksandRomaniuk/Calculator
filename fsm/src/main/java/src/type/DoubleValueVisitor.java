package src.type;

public class DoubleValueVisitor implements ValueVisitor {

    private final boolean throwExceptionPermission;
    private Double doubleValue;
    private boolean isDouble;

    private DoubleValueVisitor(boolean throwExceptionPermission) {
        this.throwExceptionPermission = throwExceptionPermission;
    }

    public static Double read(Value value) {
        DoubleValueVisitor doubleVisitor = new DoubleValueVisitor(true);

        value.accept(doubleVisitor);

        return doubleVisitor.getDoubleValue();
    }

    public static Boolean isDouble(Value value) {

        DoubleValueVisitor visitor = new DoubleValueVisitor(false);

        value.accept(visitor);

        return visitor.isDouble;

    }

    @Override
    public void visit(DoubleValue value) {

        doubleValue = value.getValue();

        isDouble = true;
    }

    @Override
    public void visit(BooleanValue value) {

        if (throwExceptionPermission) {
            throw new IllegalArgumentException("Type mismatch");
        }

        isDouble = false;
    }

    private double getDoubleValue() {
        return doubleValue;
    }
}
