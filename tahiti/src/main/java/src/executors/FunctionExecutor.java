package src.executors;


import fsm.CharSequenceReader;
import fsm.ResolvingException;
import src.runtime.ScriptContext;
import src.util.ScriptElementExecutor;

public class FunctionExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutor factoryExecutor;

    public FunctionExecutor(ScriptElementExecutor factoryExecutor) {
        this.factoryExecutor = factoryExecutor;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException {

        return factoryExecutor.execute(inputChain, output);
    }
}
