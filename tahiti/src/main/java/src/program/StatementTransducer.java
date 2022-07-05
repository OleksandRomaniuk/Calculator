package src.program;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.Transducer;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ScriptElementExecutor;

class StatementTransducer implements Transducer<ScriptContext, ExecutionException> {

    private final ScriptElementExecutor executor;

    public StatementTransducer(ScriptElementExecutor executor) {

        this.executor = Preconditions.checkNotNull(executor);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, outputChain);

        return executor.execute(inputChain, outputChain);
    }
}
