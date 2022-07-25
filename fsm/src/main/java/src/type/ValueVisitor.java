package src.type;

public interface ValueVisitor {
    void visit(DoubleValue value);

    void visit(BooleanValue value);

    void visit(StringValue value);
}
