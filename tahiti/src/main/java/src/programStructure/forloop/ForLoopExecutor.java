package src.programStructure.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ProgramElementExecutor;
import src.util.ProgramFactory;


/**
 * Implementation of {@link ProgramElementExecutor}
 */

public class ForLoopExecutor implements ProgramElementExecutor {

    private final ProgramFactory factory;

    public ForLoopExecutor(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        var forLoopMachine = ForLoopMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        var forLoopOutputChain = new ForLoopOutputChain(output);

        return forLoopMachine.run(inputChain, forLoopOutputChain);
    }
}
