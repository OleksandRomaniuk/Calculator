package src.programFeatures.whileoperator;



import src.runtime.ProgramContext;
import src.runtime.WithContext;

public class WhileOperatorContext implements WithContext {

    private final ProgramContext programContext;

    private boolean condition;

    private int position;

    WhileOperatorContext(ProgramContext context) {
        this.programContext = context;
    }

    void setConditionValue(boolean condition) {
        this.condition = condition;
    }

    void setPosition(int position) {
        this.position = position;
    }

    int getPosition() {
        return position;
    }

    boolean getConditionValue() {
        return condition;
    }

    @Override
    public ProgramContext getScriptContext() {
        return programContext;
    }

    @Override
    public boolean isParseOnly() {
        return programContext.isParseOnly();
    }
}
