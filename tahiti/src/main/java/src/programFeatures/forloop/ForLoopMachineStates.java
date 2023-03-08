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
    INITIALISE_VARIABLE_STATEMENT,
    CONDITION_STATEMENT,
    UPDATE_VARIABLE_STATEMENT,
    PARSE_UPDATE_VARIABLE_STATEMENT,
    LIST_OF_STATEMENTS,
    FIRST_SEPARATOR,
    SECOND_SEPARATOR
}
