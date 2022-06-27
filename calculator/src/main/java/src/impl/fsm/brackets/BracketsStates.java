package src.impl.fsm.brackets;

/**
 * {@code BracketsStates} is an enumeration of states
 * that used in {@link BracketsMachine}.
 */

enum BracketsStates {

    START,
    OPENING_BRACKET,
    EXPRESSION,
    CLOSING_BRACKET,
    FINISH
}

