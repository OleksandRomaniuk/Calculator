package src.programStructure.forloop;


import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.util.ExecutionException;
import src.util.ProgramFactory;

import static src.programStructure.forloop.ForLoopStates.*;

final class ForLoopMachine extends FiniteStateMachine<ForLoopStates, ForLoopOutputChain, ExecutionException> {

    private ForLoopMachine(TransitionMatrix<ForLoopStates> matrix, ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower, boolean allowedSkippingWhitespaces) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.autoTransition());

        registerTransducer(FOR_KEYWORD, new KeywordsTransducer<>("for"));

        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));

        registerTransducer(READ_VARIABLE, new ReadVariableTransducer(factory));

        registerTransducer(SEMICOLON, Transducer.checkAndPassChar(';'));

        registerTransducer(FOR_LOOP_RELATION, new ConditionStatementTransducer(factory));

        registerTransducer(SEMICOLON_2, Transducer.checkAndPassChar(';'));

        registerTransducer(UPDATING_VALUE, new UpdateForLoopVariableTransducer(factory));

        registerTransducer(UPDATE_VAlUE, new UpdateVariableTransducer(factory));

        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));

        registerTransducer(FOR_LOOP_PROGRAM, new CodeBlockTransducer<ForLoopOutputChain>(factory));
    }

    public static ForLoopMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<ForLoopStates> matrix =
                TransitionMatrix.<ForLoopStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .withTemporaryState(FOR_KEYWORD)

                        .allowTransition(START, FOR_KEYWORD)

                        .allowTransition(FOR_KEYWORD, OPENING_BRACKET)

                        .allowTransition(OPENING_BRACKET, READ_VARIABLE)

                        .allowTransition(READ_VARIABLE, SEMICOLON)

                        .allowTransition(SEMICOLON, FOR_LOOP_RELATION)

                        .allowTransition(FOR_LOOP_RELATION, SEMICOLON_2)

                        .allowTransition(SEMICOLON_2, UPDATING_VALUE)

                        .allowTransition(UPDATING_VALUE, CLOSING_BRACKET)

                        .allowTransition(CLOSING_BRACKET, FOR_LOOP_PROGRAM)

                        .allowTransition(FOR_LOOP_RELATION, FOR_LOOP_PROGRAM)

                        .allowTransition(FOR_LOOP_PROGRAM, UPDATE_VAlUE)

                        .allowTransition(UPDATE_VAlUE, FOR_LOOP_RELATION)

                        .allowTransition(FOR_LOOP_PROGRAM, FINISH)

                        .build();

        return new ForLoopMachine(matrix, factory, exceptionThrower, true);
    }
}