package src.fsm.brackets;


/**
 *
 * List of all possible states in {@link BracketsMachine}
 */

enum BracketsStates {

    START,
    OPENING_BRACKET,
    EXPRESSION,
    CLOSING_BRACKET,
    FINISH
}

