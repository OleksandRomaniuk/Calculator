package src.programStructure.switchoperator;


import src.CharSequenceReader;
import src.Transducer;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramElementExecutor;
import src.util.ProgramFactory;

/**
 * Transducer for comparing value in switch operator
 */

class ComparedValueTransducer implements Transducer<SwitchContext, ExecutionException> {

    private final ProgramFactory factory;

    ComparedValueTransducer(ProgramFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchContext outputChain) throws ExecutionException {
        ProgramElementExecutor variableExecutor = factory.create(ProgramElement.READ_VARIABLE);

        if (variableExecutor.execute(inputChain, outputChain.getScriptContext())) {

            outputChain.setCaseValue(outputChain.getScriptContext().systemStack().current().result());
            return true;
        }

        return false;
    }
}
