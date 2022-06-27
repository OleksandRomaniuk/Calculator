package src.program;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.ResolvingException;
import fsm.Transducer;
import src.runtime.ScriptContext;
import src.util.ScriptElementExecutor;


class StatementTransducer implements Transducer<ScriptContext> {

    private final ScriptElementExecutor executor;

    public StatementTransducer(ScriptElementExecutor executor) {

        this.executor = Preconditions.checkNotNull(executor);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ResolvingException {

        return executor.execute(inputChain, outputChain);
    }
}
