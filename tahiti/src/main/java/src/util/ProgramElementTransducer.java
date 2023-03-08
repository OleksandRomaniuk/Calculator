package src.util;


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
 * that used to create and execute any type of {@link ProgramElementExecutor}.
 */

public class ProgramElementTransducer implements Transducer<ProgramContext, ExecutionException> {

    private final ProgramFactory factory;

    private final ProgramElement programElement;

    public ProgramElementTransducer(ProgramElement resolver,
                                    ProgramFactory factory) {
        this.programElement = Preconditions.checkNotNull(resolver);
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramContext outputChain) throws ExecutionException {

        ProgramElementExecutor elementExecutor = factory.create(programElement);

        int startPosition = inputChain.position();

        boolean executeResult = elementExecutor.execute(inputChain, outputChain);

        if (!executeResult) {

            inputChain.setPosition(startPosition);

            return false;
        }

        return true;
    }
}
