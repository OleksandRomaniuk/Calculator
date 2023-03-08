package src.programFeatures.switchoperator;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.tahiti.*;

/**
 * Implementation of {@link Transducer}
 * that produce compared value of switch operator
 */

class ComparedValueTransducer implements Transducer<SwitchOperatorContext, ExecutionException> {

    private final ProgramFactory factory;

    ComparedValueTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchOperatorContext outputChain) throws ExecutionException {
        ProgramElementExecutor variableExecutor = factory.create(ProgramElement.READ_VARIABLE);

        if (!outputChain.isParseOnly()) {
            outputChain.getScriptContext().systemStack().create();
        }

        if (variableExecutor.execute(inputChain, outputChain.getScriptContext())) {

            outputChain.setComparedValue(outputChain.getScriptContext().systemStack().close().result());
            return true;
        }

        if (!outputChain.isParseOnly()) {
            outputChain.getScriptContext().systemStack().close();
        }

        return false;
    }
}
