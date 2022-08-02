package src.programStructure.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramFactory;


/**
 * Implementation of {@link Transducer} for variable initialisation.
 */

class ReadVariableTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ProgramFactory factory;

    ReadVariableTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        outputChain.setInitialParsingStatus(outputChain.isParseOnly());

        var initialiseVariableExecutor = factory.create(ProgramElement.INIT_VAR);

        return initialiseVariableExecutor.execute(inputChain, outputChain.getScriptContext());
    }
}
