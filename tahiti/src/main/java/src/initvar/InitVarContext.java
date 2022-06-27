package src.initvar;


import src.runtime.ScriptContext;
import src.util.WithContext;

public class InitVarContext implements WithContext {

    private final ScriptContext scriptContext;

    private String variableName;

    private Double variableValue;

    public InitVarContext(ScriptContext scriptContext) {

        this.scriptContext = scriptContext;
    }

    String getVariableName() {
        return variableName;
    }

    void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    Double getVariableValue() {
        return variableValue;
    }

    void setVariableValue(Double variableValue) {
        this.variableValue = variableValue;
    }

    @Override
    public ScriptContext getContext() {
        return scriptContext;
    }
}
