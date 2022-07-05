package src.initvar;

import com.google.common.base.Preconditions;

import fsm.CharSequenceReader;
import fsm.Transducer;
import fsm.type.Value;

import src.util.ExecutionException;
import src.util.ScriptElementExecutor;


public class VariableExpressionTransducer implements Transducer<InitVarContext, ExecutionException> {

    private final ScriptElementExecutor expressionExecutor;

    public VariableExpressionTransducer(ScriptElementExecutor executor) {
        this.expressionExecutor = Preconditions.checkNotNull(executor);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, InitVarContext outputChain) throws ExecutionException {


        Preconditions.checkNotNull(outputChain.getScriptContext(), "output is null");

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if(outputChain.isParseonly()){
                return true;
            }

            Value variableValue = outputChain.getScriptContext().systemStack().current().peekResult();

            outputChain.setVariableValue(variableValue);

            return true;
        }

        return false;
    }
}
