package src.program;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ProgramElementExecutor;

class StatementTransducer implements Transducer<ScriptContext, ExecutionException> {

    private final ProgramElementExecutor executor;

    public StatementTransducer(ProgramElementExecutor executor) {

        this.executor = Preconditions.checkNotNull(executor);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, outputChain);

        return executor.execute(inputChain, outputChain);
    }
}
