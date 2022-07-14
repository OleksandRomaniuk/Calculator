package src.util;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.ScriptContext;


public class ExecutorProgramElementTransducer implements Transducer<ScriptContext, ExecutionException> {

    private final ProgramFactory factory;

    private final ProgramElement programElement;

    public ExecutorProgramElementTransducer(ProgramElement resolver,
                                            ProgramFactory factory) {
        this.programElement = Preconditions.checkNotNull(resolver);
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        ProgramElementExecutor elementExecutor = factory.create(programElement);

        boolean executeResult = elementExecutor.execute(inputChain, outputChain);

        return executeResult;
    }
}
