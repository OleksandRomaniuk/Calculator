package src.programStructure.switchoperator;


import src.runtime.ScriptContext;
import src.runtime.WithContext;
import src.type.Value;

import java.util.Objects;

/**
 * Simple class for saving output chain for switch operator
 */

public class SwitchContext implements WithContext {

    private final ScriptContext scriptContext;

    private Value caseValue;

    private boolean caseExecuted;

    public SwitchContext(ScriptContext scriptContext) {

        this.scriptContext = scriptContext;
    }

    void setCaseValue(Value caseValue) {

        this.caseValue = caseValue;
    }

    boolean checkCondition(Value caseValue) {

        return Objects.equals(this.caseValue, caseValue);
    }

    boolean isCaseExecuted() {

        return caseExecuted;
    }

    void setCaseExecuted() {

        this.caseExecuted = true;
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
