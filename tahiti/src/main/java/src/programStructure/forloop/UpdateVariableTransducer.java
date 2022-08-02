package src.programStructure.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramFactory;


/**
 * Implementation of {@link Transducer} for updating variable in for loop.
 */

class UpdateVariableTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ProgramFactory factory;

    UpdateVariableTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        var updateVariableExecutor = factory.create(ProgramElement.STATEMENT);

        if (updateVariableExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {

                return true;
            }

            inputChain.setPosition(outputChain.ConditionPosition());

            return true;
        }

        return false;
    }
}
