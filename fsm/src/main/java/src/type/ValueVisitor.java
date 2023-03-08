package src.type;

/**
 * Realisation of visitor pattern to introduce appropriate behavior depending on the input type data
 */

public interface ValueVisitor {
    void visit(DoubleValue value);

    void visit(BooleanValue value);

    void visit(StringValue value);

}
