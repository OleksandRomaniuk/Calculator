package src.impl.fsm.number;

import src.impl.fsm.brackets.BracketsMachine;

/**
 * List of all possible states in {@link NumberStateMachine}
 *
 */

public enum NumberState {

    START,
    NEGATIVE_SIGN,
    INTEGER_DIGIT,
    DOT,
    FLOATING_INTEGER,
    FINISH
}


