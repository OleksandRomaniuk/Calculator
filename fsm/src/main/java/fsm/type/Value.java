package fsm.type;

public interface Value {

    void accept(ValueVisitor visitor);
}
