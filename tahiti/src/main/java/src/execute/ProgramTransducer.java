package src.execute;


import com.google.common.base.Preconditions;

import fsm.CharSequenceReader;
import fsm.ResolvingException;
import fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.runtime.ScriptContext;
import src.util.ScriptElementExecutor;

public class ProgramTransducer implements Transducer<ScriptContext> {

    private final ScriptElementExecutor executor;

    ProgramTransducer(ScriptElementExecutor executor) {

        this.executor = Preconditions.checkNotNull(executor);
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ResolvingException {


        return executor.execute(inputChain, outputChain);
    }
}
