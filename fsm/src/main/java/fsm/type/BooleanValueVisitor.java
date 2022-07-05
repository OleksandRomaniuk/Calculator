package fsm.type;

public class BooleanValueVisitor implements ValueVisitor{

    private boolean booleanValue;

    @Override
    public void visit(DoubleValue value) {
        throw new IllegalArgumentException("Type mismatch: expected boolean but double provided");
    }

    @Override
    public void visit(BooleanValue value) {

        booleanValue = value.getBooleanValue();
    }

    public boolean getBooleanValue(){
        return booleanValue;
    }

    public static Boolean read(Value value){
        BooleanValueVisitor booleanValueVisitor = new BooleanValueVisitor();

        value.accept(booleanValueVisitor);

        return booleanValueVisitor.getBooleanValue();
    }
}
