package src.programFeatures.forloop;

import src.*;
import src.util.CodeBlockTransducer;
import src.util.KeywordTransducer;
import src.tahiti.*;

import static src.programFeatures.forloop.ForLoopMachineStates.*;


/**
 * Implementation of {@link FiniteStateMachine} which is intended to process the for loop.
 */

final class ForLoopMachine extends FiniteStateMachine<ForLoopMachineStates, ForLoopContext, ExecutionException> {



    private ForLoopMachine(TransitionMatrix<ForLoopMachineStates> matrix, ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {
        super(matrix, exceptionThrower);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.<ForLoopContext, ExecutionException>autoTransition()
                .and((inputChain, outputChain) -> {

                    outputChain.prohibitParseOnly();

                    return true;
                })
        );

        registerTransducer(FOR_KEYWORD, new KeywordTransducer<>("for"));

        registerTransducer(OPENING_BRACKET, Transducer.checkAndPassChar('('));

        registerTransducer(INITIALISE_VARIABLE, new InitialiseVariableTransducer(factory));

        registerTransducer(SEPARATOR, Transducer.<ForLoopContext, ExecutionException>checkAndPassChar(';')
                .and((inputChain, outputChain) -> {

                    outputChain.setConditionPosition(inputChain.position());

                    return true;
                })
        );

        registerTransducer(CONDITION_STATEMENT, new ConditionStatementTransducer(factory));

        registerTransducer(SEPARATOR_SECOND, Transducer.<ForLoopContext, ExecutionException>checkAndPassChar(';'));

        registerTransducer(PARSE_STATEMENT, new ParseUpdateVariableStatementTransducer(factory));

        registerTransducer(UPDATE_VARIABLE, new UpdateVariableTransducer(factory));

        registerTransducer(CLOSING_BRACKET, Transducer.checkAndPassChar(')'));

        registerTransducer(BLOCK_STATEMENTS, new CodeBlockTransducer<ForLoopContext>(factory)
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

                        .allowTransition(OPENING_BRACKET, INITIALISE_VARIABLE)

                        .allowTransition(INITIALISE_VARIABLE, SEPARATOR)

                        .allowTransition(SEPARATOR, CONDITION_STATEMENT)

                        .allowTransition(CONDITION_STATEMENT, SEPARATOR_SECOND)

                        .allowTransition(SEPARATOR_SECOND, PARSE_STATEMENT)

                        .allowTransition(PARSE_STATEMENT, CLOSING_BRACKET)

                        .allowTransition(CLOSING_BRACKET, BLOCK_STATEMENTS)

                        .allowTransition(CONDITION_STATEMENT, BLOCK_STATEMENTS)

                        .allowTransition(BLOCK_STATEMENTS, UPDATE_VARIABLE)

                        .allowTransition(UPDATE_VARIABLE, CONDITION_STATEMENT)

                        .allowTransition(BLOCK_STATEMENTS, FINISH)

                        .build();

        return new ForLoopMachine(matrix, factory, exceptionThrower);
    }

}
    