package src.programStructure.forloop;


import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.Transducer;
import src.runtime.WithContext;
import src.util.ExecutionException;
import src.util.ProgramFactory;

import java.util.List;

public class CodeBlockTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final ProgramFactory factory;

    public CodeBlockTransducer(ProgramFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {
        FiniteStateMachine<Object, O, ExecutionException> machine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(),
                List.of(Transducer.checkAndPassChar('{'),
                        new StatementListTransducer<O>(factory).named("Statement list inside braces"),
                        Transducer.checkAndPassChar('}')
                ));

        return machine.run(inputChain, outputChain);
    }
}
