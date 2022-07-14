package src.programStructure.initvar;

import com.google.common.base.Preconditions;
import src.runtime.ScriptContext;
import src.runtime.WithContext;
import src.type.Value;


/**
 * InitVarContext is a class that used for variable initialisation, as an output for InitVarMachine
 */

public class InitVarContext implements WithContext {

    private final ScriptContext scriptContext;

    private String variableName;

    private Value variableValue;

    public InitVarContext(ScriptContext scriptContext) {

        this.scriptContext = scriptContext;
    }

    public String getVariableName() {
        return variableName;
    }

    public void setVariableName(String variableName) {
        this.variableName = Preconditions.checkNotNull(variableName);
    }

    public Value getVariableValue() {
        return variableValue;
    }

    void setVariableValue(Value variableValue) {
        this.variableValue = Preconditions.checkNotNull(variableValue);
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
