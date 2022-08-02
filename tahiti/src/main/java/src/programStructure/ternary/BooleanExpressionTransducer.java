package src.programStructure.ternary;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.type.BooleanValueVisitor;
import src.type.Value;
import src.util.ExecutionException;
import src.util.ProgramElementExecutor;


class BooleanExpressionTransducer implements Transducer<TernaryOperatorContext, ExecutionException> {

    private final ProgramElementExecutor booleanExpressionExecutor;

    BooleanExpressionTransducer(ProgramElementExecutor booleanExpressionExecutor) {
        this.booleanExpressionExecutor = booleanExpressionExecutor;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, TernaryOperatorContext outputChain) throws ExecutionException {

        Preconditions.checkNotNull(outputChain, "InitVarContext is null");

        if (booleanExpressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            Value ternaryOperatorCondition = outputChain.getScriptContext().systemStack().current().result();

            outputChain.setTernaryState(BooleanValueVisitor.read(ternaryOperatorCondition));

            return true;
        }

        return false;
    }
}
