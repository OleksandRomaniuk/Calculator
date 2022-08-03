package src.programFeatures.ternary;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.tahiti.*;

/**
 * Implementation of {@link Transducer} Transducer used to evaluate
 * expressions inside {@link TernaryOperatorMachine}.
 */

class TernaryExpressionOperatorTransducer implements Transducer<TernaryOperatorContext, ExecutionException> {

    private final ProgramElementExecutor executor;

    TernaryExpressionOperatorTransducer(ProgramElementExecutor executor) {
        this.executor = Preconditions.checkNotNull(executor);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, TernaryOperatorContext outputChain) throws ExecutionException {
        Preconditions.checkNotNull(inputChain, outputChain);

        return executor.execute(inputChain, outputChain.getScriptContext());
    }
}
