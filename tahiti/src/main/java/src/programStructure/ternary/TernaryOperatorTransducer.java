package src.programStructure.ternary;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.util.ExecutionException;
import src.util.ProgramElementExecutor;


class TernaryOperatorTransducer implements Transducer<TernaryOperatorContext, ExecutionException> {

    private final ProgramElementExecutor executor;

    TernaryOperatorTransducer(ProgramElementExecutor executor) {
        this.executor = executor;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, TernaryOperatorContext outputChain) throws ExecutionException {
        Preconditions.checkNotNull(inputChain, outputChain);

        return executor.execute(inputChain, outputChain.getScriptContext());
    }
}
