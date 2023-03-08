package src.programFeatures.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.tahiti.*;


/**
 * Implementation of {@link Transducer} that create and execute {@link ProgramElementExecutor} for variable initialisation.
 */

class InitialiseVariableTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ProgramFactory factory;

    InitialiseVariableTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        outputChain.setInitialParsingStatus(outputChain.isParseOnly());

        ProgramElementExecutor initialiseVariableExecutor = factory.create(ProgramElement.INIT_VAR);

        return initialiseVariableExecutor.execute(inputChain, outputChain.getScriptContext());
    }
}
