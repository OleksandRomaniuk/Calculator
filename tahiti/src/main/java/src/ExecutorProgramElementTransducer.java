package src;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.ResolvingException;
import fsm.Transducer;
import src.runtime.ScriptContext;
import src.util.ScriptElement;
import src.util.ScriptElementExecutor;
import src.util.ScriptElementExecutorFactory;


public class ExecutorProgramElementTransducer implements Transducer<ScriptContext> {

    private final ScriptElementExecutorFactory factory;

    private final ScriptElement scriptElement;

    public ExecutorProgramElementTransducer(ScriptElement resolver,
                                            ScriptElementExecutorFactory factory) {
        this.scriptElement = Preconditions.checkNotNull(resolver);
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ResolvingException {

        ScriptElementExecutor elementExecutor = factory.create(scriptElement);

        boolean executeResult = elementExecutor.execute(inputChain, outputChain);

        return executeResult;
    }
}
