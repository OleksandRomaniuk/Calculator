package src.booleanOperator;

import src.fsm.expression.ExpressionMachine;

/**
 * {@code ExpressionState} is an enumeration of states
 * that used in {@link ExpressionMachine}.
 */

public enum BooleanStates {

    START,
    OPERAND,
    BOOLEAN_OPERATOR,
    FINISH
}


