package src.programStructure.switchoperator;


import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.Transducer;
import src.util.ExecutionException;
import src.util.ProgramFactory;

import java.util.List;

/**
 * Implementation of {@link FiniteStateMachine} which is intended to process
 * switch operator in my language.
 */
class SwitchStatementListTransducer implements Transducer<SwitchContext, ExecutionException> {

    private final ProgramFactory factory;

    SwitchStatementListTransducer(ProgramFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchContext outputChain) throws ExecutionException {

        FiniteStateMachine<Object, SwitchContext, ExecutionException> machine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(),
                List.of(Transducer.checkAndPassChar('{'),
                        new StatementListTransducer<SwitchContext>(factory).named("Statement list inside case"),
                        Transducer.checkAndPassChar('}'),
                        new PermissionTransducer<>(outputChain.isCaseExecuted())
                ));

        return machine.run(inputChain, outputChain);
    }
}
