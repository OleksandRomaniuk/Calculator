package src.programStructure.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramFactory;


/**
 * Implementation of {@link Transducer} for  updating variable in for loop.
 */

class UpdateForLoopVariableTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ProgramFactory factory;

    UpdateForLoopVariableTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        outputChain.parseOnly();

        var updateVariableExecutor = factory.create(ProgramElement.STATEMENT);

        outputChain.UpdateVariablePosition(inputChain.position());

        if (updateVariableExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.ConditionValue()) {

                outputChain.prohibitParseOnly();
            }

            return true;
        }

        return false;
    }
}
