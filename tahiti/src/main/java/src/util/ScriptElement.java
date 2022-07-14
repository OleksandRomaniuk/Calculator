package src.util;

/**
 * {@code ScriptElement} is an enumeration of program elements
 * that can be used in {@link ScriptElementExecutorFactory}.
 */

public enum ScriptElement {
    NUMBER,
    NUMERIC_EXPRESSION,
    RELATIONAL_EXPRESSION,
    EXPRESSION,
    OPERAND,
    BRACKETS,
    FUNCTION,
    STATEMENT,
    INIT_VAR,
    PROGRAM,
    PROCEDURE,
    READ_VARIABLE,
    WHILE_OPERATOR,
    LOGICAL_EXPRESSION,
    LOGICAL_EXPRESSION_OPERAND
}
