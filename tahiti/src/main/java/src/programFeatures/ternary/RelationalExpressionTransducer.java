package src.programFeatures.ternary;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.type.BooleanValueVisitor;
import src.type.Value;
import src.tahiti.*;


/**
 * Implementation of {@link Transducer}
 * that used for producing result of relational expression to {@link TernaryOperatorContext}.
 */

class RelationalExpressionTransducer implements Transducer<TernaryOperatorContext, ExecutionException> {

    private final ProgramElementExecutor relationalExpressionExecutor;

    RelationalExpressionTransducer(ProgramElementExecutor relationalExpressionExecutor) {
        this.relationalExpressionExecutor = Preconditions.checkNotNull(relationalExpressionExecutor);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, TernaryOperatorContext outputChain) throws ExecutionException {

        outputChain.setInitialParsingStatus(outputChain.isParseOnly());

        if (!outputChain.isParseOnly()) {
            outputChain.getScriptContext().systemStack().create();
        }

        if (relationalExpressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (!outputChain.isParseOnly()) {

                Value ternaryOperatorCondition = outputChain.getScriptContext().systemStack().close().result();

                outputChain.setTernaryOperatorCondition(BooleanValueVisitor.read(ternaryOperatorCondition));
            }

            return true;
        }

        if (!outputChain.isParseOnly()) {
            outputChain.getScriptContext().systemStack().close();
        }

        return false;
    }
}
