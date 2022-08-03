package src.programFeatures.forloop;

import src.*;
import src.util.CodeBlockTransducer;
import src.util.KeywordTransducer;
import src.tahiti.*;

import static src.programFeatures.forloop.ForLoopMachineStates.*;


/**
 * Implementation of {@link FiniteStateMachine} which is intended to process the for loop.
 */

final class ForLoopMachine extends FiniteStateMachine<ForLoopMachineStates, ForLoopOutputChain, ExecutionException> {



    private ForLoopMachine(TransitionMatrix<ForLoopMachineStates> matrix, ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.<ForLoopOutputChain, ExecutionException>autoTransition()
                .and((inputChain, outputChain) -> {

                    outputChain.prohibitParseOnly();

                    return true;
                })
        );

        registerTransducer(FOR_KEYWORD, new KeywordTransducer<>("for"));

        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));

        registerTransducer(INITIALISE_VARIABLE_STATEMENT, new InitialiseVariableTransducer(factory));

        registerTransducer(FIRST_SEPARATOR, Transducer.<ForLoopOutputChain, ExecutionException>checkAndPassChar(';')
                .and((inputChain, outputChain) -> {

                    outputChain.setConditionPosition(inputChain.position());

                    return true;
                })
        );

        registerTransducer(CONDITION_STATEMENT, new ConditionStatementTransducer(factory));

        registerTransducer(SECOND_SEPARATOR, Transducer.<ForLoopOutputChain, ExecutionException>checkAndPassChar(';'));

        registerTransducer(PARSE_UPDATE_VARIABLE_STATEMENT, new ParseUpdateVariableStatementTransducer(factory));

        registerTransducer(UPDATE_VARIABLE_STATEMENT, new UpdateVariableTransducer(factory));

        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));

        registerTransducer(LIST_OF_STATEMENTS, new CodeBlockTransducer<ForLoopOutputChain>(factory)
                .and((inputChain, outputChain) -> {

                    if (!outputChain.isParseOnly()) {

                        inputChain.setPosition(outputChain.getUpdateVariablePosition());
                    }

                    return true;
                })
        );

    }

    public static ForLoopMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        TransitionMatrix<ForLoopMachineStates> matrix =
                TransitionMatrix.<ForLoopMachineStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .withTemporaryState(FOR_KEYWORD)

                        .allowTransition(START, FOR_KEYWORD)

                        .allowTransition(FOR_KEYWORD, OPENING_BRACKET)

                        .allowTransition(OPENING_BRACKET, INITIALISE_VARIABLE_STATEMENT)

                        .allowTransition(INITIALISE_VARIABLE_STATEMENT, FIRST_SEPARATOR)

                        .allowTransition(FIRST_SEPARATOR, CONDITION_STATEMENT)

                        .allowTransition(CONDITION_STATEMENT, SECOND_SEPARATOR)

                        .allowTransition(SECOND_SEPARATOR, PARSE_UPDATE_VARIABLE_STATEMENT)

                        .allowTransition(PARSE_UPDATE_VARIABLE_STATEMENT, CLOSING_BRACKET)

                        .allowTransition(CLOSING_BRACKET, LIST_OF_STATEMENTS)

                        .allowTransition(CONDITION_STATEMENT, LIST_OF_STATEMENTS)

                        .allowTransition(LIST_OF_STATEMENTS, UPDATE_VARIABLE_STATEMENT)

                        .allowTransition(UPDATE_VARIABLE_STATEMENT, CONDITION_STATEMENT)

                        .allowTransition(LIST_OF_STATEMENTS, FINISH)

                        .build();

        return new ForLoopMachine(matrix, factory, exceptionThrower);
    }

}
    