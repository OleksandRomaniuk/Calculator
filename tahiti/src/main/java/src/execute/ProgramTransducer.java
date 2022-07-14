package src.execute;


import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ScriptElementExecutor;

public class ProgramTransducer implements Transducer<ScriptContext, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(ProgramTransducer.class);

    private final ScriptElementExecutor executor;

    ProgramTransducer(ScriptElementExecutor executor) {

        this.executor = Preconditions.checkNotNull(executor);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, outputChain);

        return executor.execute(inputChain, outputChain);
    }
}
