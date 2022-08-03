package src.programFeatures.switchoperator;



import src.runtime.ProgramContext;
import src.runtime.WithContext;
import src.type.Value;

import java.util.Objects;

/**
 * Implementation of {@link WithContext}
 * that used as output chain for {@link SwitchOperatorMachine}.
 */

public class SwitchOperatorContext implements WithContext {

    private final ProgramContext programContext;

    private Value comparedValue;

    private boolean caseExecuted;

    private boolean initialParsingStatus;

    void setInitialParsingStatus(boolean initialParsingStatus) {
        this.initialParsingStatus = initialParsingStatus;
    }


    void setParsePermission(boolean parsingPermission){

        if (!initialParsingStatus) {
            programContext.setParsingPermission(parsingPermission);
        }
    }

    public SwitchOperatorContext(ProgramContext programContext) {

        this.programContext = programContext;
    }

    void setComparedValue(Value comparedValue) {

        this.comparedValue = comparedValue;
    }

    boolean checkCondition(Value caseValue) {

        return Objects.equals(comparedValue, caseValue);
    }

    boolean isCaseExecuted() {

        return caseExecuted;
    }

    void setCaseExecuted() {

        this.caseExecuted = true;
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
