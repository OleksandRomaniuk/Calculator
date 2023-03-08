package src.program;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.ProgramContext;
import src.tahiti.*;

/**
 * Implementations of {@link ProgramElementExecutor}
 * that used to launch machine that parse and execute statements through {@link ProgramFactory}.
 */

class StatementTransducer implements Transducer<ProgramContext, ExecutionException> {

    private final ProgramFactory factory;

    StatementTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, outputChain);

        ProgramElementExecutor executor = factory.create(ProgramElement.STATEMENT);

        return executor.execute(inputChain, outputChain);
    }
}
