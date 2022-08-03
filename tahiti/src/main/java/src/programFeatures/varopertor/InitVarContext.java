package src.programFeatures.varopertor;

import com.google.common.base.Preconditions;
import src.runtime.ProgramContext;
import src.runtime.WithContext;
import src.type.Value;


/**
 * Implementation of {@link WithContext} that used as an output chain for {@link InitVarMachine}.
 */

public class InitVarContext implements WithContext {

    private final ProgramContext programContext;

    private String variableName;

    private Value variableValue;

    public InitVarContext(ProgramContext programContext) {

        this.programContext = programContext;
    }

    public String getVariableName() {
        return variableName;
    }

    void setVariableName(String variableName) {
        this.variableName = Preconditions.checkNotNull(variableName);
    }

    public Value getVariableValue() {
        return variableValue;
    }

    void setVariableValue(Value variableValue) {
        this.variableValue = Preconditions.checkNotNull(variableValue);
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
