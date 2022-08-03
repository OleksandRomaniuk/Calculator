package src.programFeatures.ternary;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElementExecutor;
import src.tahiti.ProgramFactory;


/**
 * Implementation of {@link ProgramElementExecutor} that create and run {@link TernaryOperatorMachine}.
 */

public class TernaryOperatorExecutor implements ProgramElementExecutor {

    private final ProgramFactory factory;

    public TernaryOperatorExecutor(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        TernaryOperatorMachine ternaryOperatorMachine = TernaryOperatorMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        TernaryOperatorContext ternaryOperatorContext = new TernaryOperatorContext(output);

        return ternaryOperatorMachine.run(inputChain, ternaryOperatorContext);
    }
}
