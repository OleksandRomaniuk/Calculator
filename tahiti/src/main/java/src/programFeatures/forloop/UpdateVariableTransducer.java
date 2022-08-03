package src.programFeatures.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.tahiti.*;

/**
 * Implementation of {@link Transducer} that create and execute {@link ProgramElementExecutor}
 * for statement that must update variable in for loop.
 */

class UpdateVariableTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ProgramFactory factory;

    UpdateVariableTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        ProgramElementExecutor updateVariableExecutor = factory.create(ProgramElement.STATEMENT);

        if (updateVariableExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {

                return true;
            }

            inputChain.setPosition(outputChain.getConditionPosition());

            return true;
        }

        return false;
    }
}
