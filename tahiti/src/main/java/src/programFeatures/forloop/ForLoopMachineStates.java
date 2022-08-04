package src.programFeatures.forloop;

/**
 * List of possible states in {@link ForLoopMachine}.
 */

enum ForLoopMachineStates {

    START,
    FINISH,
    FOR_KEYWORD,
    OPENING_BRACKET,
    CLOSING_BRACKET,
    INITIALISE_VARIABLE,
    CONDITION_STATEMENT,
    UPDATE_VARIABLE,
    PARSE_STATEMENT,
    BLOCK_STATEMENTS,
    SEPARATOR,
    SEPARATOR_SECOND
}
