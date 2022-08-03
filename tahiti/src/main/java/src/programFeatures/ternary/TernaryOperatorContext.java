package src.programFeatures.ternary;


import src.runtime.ProgramContext;
import src.runtime.WithContext;

/**
 * Simple holder class that can be used like an output chain for {@link TernaryOperatorMachine}.
 */

public class TernaryOperatorContext implements WithContext {

    private final ProgramContext programContext;

    private boolean ternaryOperatorCondition;

    private boolean initialParsingStatus;

    void setInitialParsingStatus(boolean initialParsingStatus) {
        this.initialParsingStatus = initialParsingStatus;
    }

    void setParseOnlyMode() {

        if (!initialParsingStatus) {
            programContext.setParsingPermission(true);
        }
    }

    void prohibitParseOnly() {

        if (!initialParsingStatus) {
            programContext.setParsingPermission(false);
        }
    }

    public TernaryOperatorContext(ProgramContext programContext) {
        this.programContext = programContext;
    }

    boolean ternaryOperatorCondition() {
        return ternaryOperatorCondition;
    }

    void setTernaryOperatorCondition(boolean ternaryOperatorCondition) {
        this.ternaryOperatorCondition = ternaryOperatorCondition;
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
