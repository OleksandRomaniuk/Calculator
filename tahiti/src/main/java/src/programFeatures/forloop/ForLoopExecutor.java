package src.programFeatures.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.runtime.ProgramContext;
import src.tahiti.*;


/**
 * Implementation of {@link ProgramElementExecutor} that create and run {@link ForLoopMachine}.
 */

public class ForLoopExecutor implements ProgramElementExecutor {

    private final ProgramFactory factory;

    public ForLoopExecutor(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        ForLoopMachine forLoopMachine = ForLoopMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        ForLoopOutputChain forLoopOutputChain = new ForLoopOutputChain(output);

        return forLoopMachine.run(inputChain, forLoopOutputChain);
    }
}
