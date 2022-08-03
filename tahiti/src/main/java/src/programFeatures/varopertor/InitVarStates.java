package src.programFeatures.varopertor;

/**
 * List of possible states in {@link InitVarMachine}.
 */

public enum InitVarStates {

    START,
    ASSIGN,
    NAME,
    EXPRESSION,
    FINISH
}
