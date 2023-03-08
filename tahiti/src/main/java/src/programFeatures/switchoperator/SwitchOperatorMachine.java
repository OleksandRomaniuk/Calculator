package src.programFeatures.switchoperator;

import src.*;
import src.util.KeywordTransducer;
import src.tahiti.*;

import static src.programFeatures.switchoperator.SwitchStates.*;


/**
 * Implementation of {@link FiniteStateMachine}
 * Compared value of witch operator may be a variable, but case option may be an expression.
 */

public final class SwitchOperatorMachine extends FiniteStateMachine<SwitchStates, SwitchOperatorContext, ExecutionException> {

    private SwitchOperatorMachine(TransitionMatrix<SwitchStates> matrix, ExceptionThrower<ExecutionException> exceptionThrower,
                                  ProgramFactory factory) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.autoTransition());

        registerTransducer(SWITCH_KEYWORD, new KeywordTransducer<>("switch"));

        registerTransducer(VARIABLE, new SwitchConditionTransducer(factory));

        registerTransducer(CASE, new KeywordTransducer<>("case"));

        registerTransducer(OPTION, new OptionTransducer(factory));

        registerTransducer(COLON, Transducer.checkAndPassChar(':'));

        registerTransducer(OPENING_BRACE, Transducer.checkAndPassChar('{'));

        registerTransducer(CLOSING_BRACE, Transducer.<SwitchOperatorContext, ExecutionException>checkAndPassChar('}')
                .and((inputChain, outputChain) -> {

                    outputChain.setParsePermission(false);

                    return true;
                })
        );

        registerTransducer(STATEMENT_LIST, new SwitchStatementListTransducer(factory));

        registerTransducer(DEFAULT, new KeywordTransducer<>("default"));
    }

    public static SwitchOperatorMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<SwitchStates> matrix =
                TransitionMatrix.<SwitchStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)

                        .allowTransition(START, SWITCH_KEYWORD)
                        .allowTransition(SWITCH_KEYWORD, VARIABLE)
                        .allowTransition(VARIABLE, OPENING_BRACE)
                        .allowTransition(OPENING_BRACE, CASE)
                        .allowTransition(CASE, OPTION)
                        .allowTransition(OPTION, COLON)
                        .allowTransition(COLON, STATEMENT_LIST)
                        .allowTransition(STATEMENT_LIST, CASE, DEFAULT, CLOSING_BRACE)
                        .allowTransition(DEFAULT, COLON)
                        .allowTransition(CLOSING_BRACE, FINISH)

                        .build();

        return new SwitchOperatorMachine(matrix, exceptionThrower, factory);
    }
}
