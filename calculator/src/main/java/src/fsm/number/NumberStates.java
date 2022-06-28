package src.fsm.number;

/**
 * {@code NumberState} is an enumeration of states
 * that used in {@link NumberStateMachine}.
 */

enum NumberStates {

    START,
    NEGATIVE_SIGN,
    INTEGER_DIGIT,
    DOT,
    FLOATING_INTEGER,
    FINISH
}


