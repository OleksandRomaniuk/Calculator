package src.programStructure.switchoperator;


import src.CharSequenceReader;
import src.Transducer;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramElementExecutor;
import src.util.ProgramFactory;

class OptionTransducer implements Transducer<SwitchContext, ExecutionException> {

    private final ProgramFactory factory;

    OptionTransducer(ProgramFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchContext outputChain) throws ExecutionException {

        ProgramElementExecutor expressionExecutor = factory.create(ProgramElement.EXPRESSION);

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (!outputChain.isParseOnly()) {
                if (outputChain.checkCondition(outputChain.getScriptContext().systemStack().current().result())) {
                    outputChain.setCaseExecuted();
                } else {
                    outputChain.getScriptContext().setParsingPermission(true);
                }
            }

            return true;
        }

        return false;
    }
}
