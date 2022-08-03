package src.programFeatures.forloop;

import com.google.common.base.Preconditions;
import src.runtime.ProgramContext;
import src.runtime.WithContext;


/**
 * Implementation of {@link WithContext} which used like output chain for {@link ForLoopMachine}.
 * It stores condition statement position and statement for updating variable position of for loop.
 */

class ForLoopOutputChain implements WithContext {

    private final ProgramContext programContext;

    private boolean initialParsingStatus;

    private boolean conditionValue;

    private int conditionPosition;

    private int updateVariablePosition;

    ForLoopOutputChain(ProgramContext programContext) {
        this.programContext = Preconditions.checkNotNull(programContext);
    }

    boolean getConditionValue() {
        return conditionValue;
    }

    void setConditionValue(boolean conditionValue) {
        this.conditionValue = conditionValue;
    }

    void setParseOnly() {

        if (!initialParsingStatus) {
            programContext.setParsingPermission(true);
        }
    }

    void prohibitParseOnly() {

        if (!initialParsingStatus) {
            programContext.setParsingPermission(false);
        }
    }

    void setInitialParsingStatus(boolean initialParsingStatus) {
        this.initialParsingStatus = initialParsingStatus;
    }

    int getConditionPosition() {

        return conditionPosition;
    }

    void setConditionPosition(int conditionPosition) {

        this.conditionPosition = conditionPosition;
    }

    int getUpdateVariablePosition() {
        return updateVariablePosition;
    }

    void setUpdateVariablePosition(int updateVariablePosition) {
        this.updateVariablePosition = updateVariablePosition;
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
