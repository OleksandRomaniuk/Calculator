package src.programStructure.ternary;


import src.runtime.ScriptContext;
import src.runtime.WithContext;

public class TernaryOperatorContext implements WithContext {

    private final ScriptContext scriptContext;

    private boolean ternaryOperatorCondition;

    public TernaryOperatorContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
    }

    boolean ternaryOperatorCondition() {
        return ternaryOperatorCondition;
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
