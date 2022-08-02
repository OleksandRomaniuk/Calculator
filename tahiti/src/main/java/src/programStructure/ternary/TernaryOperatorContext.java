package src.programStructure.ternary;


import src.runtime.ScriptContext;
import src.runtime.WithContext;

/**
 * Simple class for saving output chain for ternary operator
 */

public class TernaryOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean ternaryState;

    public TernaryOperatorContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

    boolean ternaryOperatorCondition() {
        return ternaryState;
    }

    void setTernaryState(boolean ternaryState) {
        this.ternaryState = ternaryState;
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
