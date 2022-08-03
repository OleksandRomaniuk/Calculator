package src.programFeatures.switchoperator;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.tahiti.*;

/**
 * Implementation of {@link Transducer}
 * Class compare result of expression to compared value of switch operator.
 */

class OptionTransducer implements Transducer<SwitchOperatorContext, ExecutionException> {

    private final ProgramFactory factory;

    OptionTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchOperatorContext outputChain) throws ExecutionException {

        ProgramElementExecutor expressionExecutor = factory.create(ProgramElement.EXPRESSION);

        if (!outputChain.isParseOnly()) {
            outputChain.getScriptContext().systemStack().create();
        }

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (!outputChain.isParseOnly()) {

                if (outputChain.checkCondition(outputChain.getScriptContext().systemStack().close().result())) {
                    outputChain.setCaseExecuted();

                } else {

                    outputChain.getScriptContext().setParsingPermission(true);
                }
            }

            return true;
        }

        outputChain.getScriptContext().systemStack().close();

        return false;
    }
}
