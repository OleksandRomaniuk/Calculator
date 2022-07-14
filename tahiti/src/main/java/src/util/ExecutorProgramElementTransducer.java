package src.util;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.ScriptContext;


public class ExecutorProgramElementTransducer implements Transducer<ScriptContext, ExecutionException> {

    private final ScriptElementExecutorFactory factory;

    private final ScriptElement scriptElement;

    public ExecutorProgramElementTransducer(ScriptElement resolver,
                                            ScriptElementExecutorFactory factory) {
        this.scriptElement = Preconditions.checkNotNull(resolver);
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        ScriptElementExecutor elementExecutor = factory.create(scriptElement);

        boolean executeResult = elementExecutor.execute(inputChain, outputChain);

        return executeResult;
    }
}
