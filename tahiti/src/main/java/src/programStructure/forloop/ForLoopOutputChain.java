package src.programStructure.forloop;

import com.google.common.base.Preconditions;
import src.runtime.ScriptContext;
import src.runtime.WithContext;

/**
 * Implementation of {@link WithContext} for saving output for {@link ForLoopMachine}.
 */

class ForLoopOutputChain implements WithContext {

    private final ScriptContext scriptContext;

    private boolean initialParsingStatus;

    private boolean conditionValue;

    private int conditionPosition;

    private int updateVariablePosition;

    ForLoopOutputChain(ScriptContext scriptContext) {
        this.scriptContext = Preconditions.checkNotNull(scriptContext);
    }

    boolean ConditionValue() {
        return conditionValue;
    }

    void ConditionValue(boolean conditionValue) {
        this.conditionValue = conditionValue;
    }

    void parseOnly() {

        if (!initialParsingStatus) {
            scriptContext.setParsingPermission(true);
        }
    }

    void prohibitParseOnly() {

        if (!initialParsingStatus) {
            scriptContext.setParsingPermission(false);
        }
    }

    void setInitialParsingStatus(boolean initialParcingStatus) {
        this.initialParsingStatus = initialParcingStatus;
    }

    int ConditionPosition() {

        return conditionPosition;
    }


    void UpdateVariablePosition(int updateVariablePosition) {
        this.updateVariablePosition = updateVariablePosition;
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
