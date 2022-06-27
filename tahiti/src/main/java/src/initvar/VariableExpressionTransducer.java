package src.initvar;

import com.google.common.base.Preconditions;

import fsm.CharSequenceReader;
import fsm.ResolvingException;
import fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.util.ScriptElementExecutor;

public class VariableExpressionTransducer implements Transducer<InitVarContext> {


    private final ScriptElementExecutor executor;

    VariableExpressionTransducer(ScriptElementExecutor executor) {
        this.executor = executor;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, InitVarContext outputChain) throws ResolvingException {

        Preconditions.checkNotNull(outputChain.getContext(), "output is null");

        if (executor.execute(inputChain, outputChain.getContext())) {

            double variableValue = outputChain.getContext().systemStack().current().peekResult();

            outputChain.setVariableValue(variableValue);

            return true;
        }

        return false;
    }
}
