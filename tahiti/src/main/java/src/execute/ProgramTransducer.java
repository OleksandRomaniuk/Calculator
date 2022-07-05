package src.execute;


import com.google.common.base.Preconditions;

import fsm.CharSequenceReader;
import fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

        if (logger.isInfoEnabled()) {

            logger.info("Working with input chain -> {}", inputChain.toString());

            logger.info("OutputChain is null : {}", (outputChain));
        }

        return executor.execute(inputChain, outputChain);
    }
}
