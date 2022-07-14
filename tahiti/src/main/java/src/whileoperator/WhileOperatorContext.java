package src.whileoperator;


import src.runtime.ScriptContext;
import src.runtime.WithContext;

public class WhileOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean condition;

    private int position;

    public WhileOperatorContext(ScriptContext context) {
        this.scriptContext = context;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return position;
    }

    public boolean isCondition() {
        return condition;
    }

    @Override
    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    @Override
    public boolean isParseonly() {
        return scriptContext.isParseonly();
    }
}
