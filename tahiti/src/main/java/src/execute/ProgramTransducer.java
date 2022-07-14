package src.execute;


import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ProgramElementExecutor;

public class ProgramTransducer implements Transducer<ScriptContext, ExecutionException> {

    private final ProgramElementExecutor executor;

    ProgramTransducer(ProgramElementExecutor executor) {

        this.executor = Preconditions.checkNotNull(executor);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, outputChain);

        return executor.execute(inputChain, outputChain);
    }
}
