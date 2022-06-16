package src.impl.fsm.operand;

import src.impl.fsm.number.NumberStateMachine;

/**
 * List of all possible states in {@link OperandStates}
 *
 */

public enum OperandStates {

    START,
    NUMBER,
    BRACKETS,
    FUNCTION,
    FINISH
}


