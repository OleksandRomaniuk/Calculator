package src.programFeatures.switchoperator;

/**
 * List of possible states in {@link SwitchOperatorMachine}.
 */

enum SwitchStates {
    START,
    FINISH,
    SWITCH_KEYWORD,
    OPENING_BRACE,
    VARIABLE,
    CASE,
    OPTION,
    COLON,
    STATEMENT_LIST,
    DEFAULT,
    CLOSING_BRACE
}
