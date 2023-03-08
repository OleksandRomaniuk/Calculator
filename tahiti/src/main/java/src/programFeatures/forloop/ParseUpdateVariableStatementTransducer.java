package src.programFeatures.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.tahiti.*;


/**
 * Implementation of {@link Transducer} that create and execute {@link ProgramElementExecutor} f
 * or statement that must update variable in for loop.
 */

class ParseUpdateVariableStatementTransducer implements Transducer<ForLoopContext, ExecutionException> {

    private final ProgramFactory factory;

    ParseUpdateVariableStatementTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopContext outputChain) throws ExecutionException {

        outputChain.setParseOnly();

        ProgramElementExecutor updateVariableExecutor = factory.create(ProgramElement.STATEMENT);

        outputChain.setUpdateVariablePosition(inputChain.position());

        if (updateVariableExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.getConditionValue()) {

                outputChain.prohibitParseOnly();
            }

            return true;
        }

        return false;
    }
}
