package src.util;

import src.CharSequenceReader;
import src.Transducer;
import src.runtime.WithContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElement;
import src.tahiti.ProgramElementExecutor;
import src.tahiti.ProgramFactory;


/**
 * Implementation of {@link Transducer}
 * that used to create and execute {@link ProgramElementExecutor} that run {@link src.program.ProgramMachine}.
 */

public class StatementListTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final ProgramFactory factory;

    public StatementListTransducer(ProgramFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        ProgramElementExecutor executor = factory.create(ProgramElement.PROGRAM);

        return executor.execute(inputChain, outputChain.getScriptContext());
    }
}
