package src.executors;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ScriptElementExecutor;


public class FunctionExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutor factoryExecutor;

    public FunctionExecutor(ScriptElementExecutor factoryExecutor) {
        this.factoryExecutor = factoryExecutor;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, output);

        return factoryExecutor.execute(inputChain, output);
    }
}
