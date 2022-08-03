package src.interpreter;


import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElement;
import src.tahiti.ProgramElementExecutor;
import src.tahiti.ProgramFactory;

/**
 * Implementation of {@link Transducer}
 * that used to execute {@link ProgramElementExecutor}
 */

public class ProgramTransducer implements Transducer<ProgramContext, ExecutionException> {

    private final ProgramFactory factory;

    ProgramTransducer(ProgramFactory factory) {

        this.factory = Preconditions.checkNotNull(factory);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, outputChain);

        ProgramElementExecutor executor = factory.create(ProgramElement.PROGRAM);

        return executor.execute(inputChain, outputChain);
    }
}
