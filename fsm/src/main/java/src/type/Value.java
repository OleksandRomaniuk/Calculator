package src.type;



public interface Value {

    void accept(ValueVisitor visitor);
}
