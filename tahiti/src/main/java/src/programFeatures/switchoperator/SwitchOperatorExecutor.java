package src.programFeatures.switchoperator;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElementExecutor;
import src.tahiti.ProgramFactory;

/**
 * Implementation of {@link ProgramElementExecutor} that create and run {@link SwitchOperatorMachine}.
 */

public class SwitchOperatorExecutor implements ProgramElementExecutor {

    private final ProgramFactory factory;

    public SwitchOperatorExecutor(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        SwitchOperatorMachine switchOperatorMachine = SwitchOperatorMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        SwitchOperatorContext switchOperatorContext = new SwitchOperatorContext(output);

        return switchOperatorMachine.run(inputChain, switchOperatorContext);
    }
}
