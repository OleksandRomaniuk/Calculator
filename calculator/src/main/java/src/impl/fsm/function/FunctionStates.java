package src.impl.fsm.function;

/**
 *
 * List of all possible states in {@link FunctionMachine}
 */

public enum FunctionStates {

    START,
    FINISH,
    IDENTIFIER,
    OPENING_BRACKET,
    CLOSING_BRACKET,
    EXPRESSION,
    SEPARATOR

}