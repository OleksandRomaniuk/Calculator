package src.impl.fsm.brackets;

/**
 *
 * List of all possible states in {@link BracketsMachine}
 */

public enum BracketsStates {

    START,
    OPENING_BRACKET,
    EXPRESSION,
    CLOSING_BRACKET,
    FINISH
}

