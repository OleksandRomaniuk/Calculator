package src.fsm.function;

/**
 * {@code NumberState} is an enumeration of states
 * that used in {@link FunctionMachine}.
 */

public enum FunctionStates {

    START,
    IDENTIFIER,
    OPENING_BRACKET,
    EXPRESSION,
    SEPARATOR,
    CLOSING_BRACKET,
    FINISH
}
