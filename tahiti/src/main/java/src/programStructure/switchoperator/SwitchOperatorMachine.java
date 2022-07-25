package src.programStructure.switchoperator;

/**
 * Implementation of {@link FiniteStateMachine} which is intended to process
 * switch operator in my language.
 */
import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.TransitionMatrix;
import src.util.ExecutionException;
import src.util.ProgramFactory;

import static src.programStructure.switchoperator.SwitchStates.*;

public final class SwitchOperatorMachine extends FiniteStateMachine<SwitchStates, SwitchContext, ExecutionException> {

    private SwitchOperatorMachine(TransitionMatrix<SwitchStates> matrix, ExceptionThrower<ExecutionException> exceptionThrower,
                                  ProgramFactory factory) {
        super(matrix, exceptionThrower, true);

        registerTransducer(START, Transducer.illegalTransition());

        registerTransducer(FINISH, Transducer.autoTransition());

        registerTransducer(SWITCH, new KeywordTransducer<>("switch"));

        registerTransducer(CASE, new KeywordTransducer<>("case"));

        registerTransducer(DEFAULT, new KeywordTransducer<>("default"));

        registerTransducer(LIST, new SwitchStatementListTransducer(factory));

        registerTransducer(VARIABLE, new SwitchVariableTransducer(factory));

        registerTransducer(OPTION, new OptionTransducer(factory));

        registerTransducer(COLON, Transducer.checkAndPassChar(':'));

        registerTransducer(OPENING_BRACKETS, Transducer.checkAndPassChar('{'));

        registerTransducer(CLOSING_BRACKETS, Transducer.<SwitchContext, ExecutionException>checkAndPassChar('}')
                .and(new PermissionTransducer<>(false)));

    }

    public static SwitchOperatorMachine create(ProgramFactory factory, ExceptionThrower<ExecutionException> exceptionThrower) {

        TransitionMatrix<SwitchStates> matrix =
                TransitionMatrix.<SwitchStates>builder()
                        .withStartState(START)
                        .withFinishState(FINISH)
                        .allowTransition(START, SWITCH)
                        .allowTransition(SWITCH, VARIABLE)
                        .allowTransition(VARIABLE, OPENING_BRACKETS)
                        .allowTransition(OPENING_BRACKETS, CASE)
                        .allowTransition(CASE, OPTION)
                        .allowTransition(OPTION, COLON)
                        .allowTransition(COLON, LIST)
                        .allowTransition(LIST, CASE, DEFAULT, CLOSING_BRACKETS)
                        .allowTransition(DEFAULT, COLON)
                        .allowTransition(CLOSING_BRACKETS, FINISH)
                        .build();

        return new SwitchOperatorMachine(matrix, exceptionThrower, factory);
    }
}
