package src.initvar;

public class InitVarContext {

    private String variableName;

    private Double variableValue;

    String getVariableName() {
        return variableName;
    }

    Double getVariableValue() {
        return variableValue;
    }

    void setVariableName(String variableName) {
        this.variableName = variableName;
    }

    void setVariableValue(Double variableValue) {
        this.variableValue = variableValue;
    }
}
