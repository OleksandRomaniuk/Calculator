package src.executors;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ProgramElementExecutor;


public class FunctionExecutor implements ProgramElementExecutor {

    private final ProgramElementExecutor factoryExecutor;

    public FunctionExecutor(ProgramElementExecutor factoryExecutor) {
        this.factoryExecutor = factoryExecutor;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, output);

        return factoryExecutor.execute(inputChain, output);
    }
}
