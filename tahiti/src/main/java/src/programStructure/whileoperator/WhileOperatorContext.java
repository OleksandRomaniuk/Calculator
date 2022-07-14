package src.programStructure.whileoperator;


import src.runtime.ScriptContext;
import src.runtime.WithContext;

public class WhileOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean condition;

    private int position;

    WhileOperatorContext(ScriptContext context) {
        this.scriptContext = context;
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
    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    @Override
    public boolean isParseOnly() {
        return scriptContext.isParseOnly();
    }
}
