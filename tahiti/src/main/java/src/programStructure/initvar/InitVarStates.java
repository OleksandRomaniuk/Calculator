package src.programStructure.initvar;

/**
 * {@code InitVarStates} is an enumeration of states that used in {@link InitVarMachine}
 */

enum InitVarStates {

    START,
    ASSIGN,
    NAME,
    EXPRESSION,
    FINISH
}
