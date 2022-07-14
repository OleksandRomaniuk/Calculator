package src.expression;

/**
 * {@code ScriptExpressionStates} is an enumeration of states that used in {@link ScriptExpressionMachine}
 */

enum ScriptExpressionStates {
    START,
    FINISH,
    LOGICAL_EXPRESSION,
    NUMERIC_EXPRESSION
}
