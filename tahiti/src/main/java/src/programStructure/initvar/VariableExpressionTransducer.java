package src.programStructure.initvar;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.CharSequenceReader;
import src.Transducer;
import src.type.Value;
import src.util.ExecutionException;
import src.util.ProgramElementExecutor;

/**
 * {@code VariableExpressionTransducer} is an implementation of {@link Transducer}
 * that call execute method of {@link ProgramElementExecutor}, take and put result of execution
 * to {@link InitVarContext}.
 */

public class VariableExpressionTransducer implements Transducer<InitVarContext, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(VariableExpressionTransducer.class);

    private final ProgramElementExecutor expressionExecutor;

    public VariableExpressionTransducer(ProgramElementExecutor executor) {
        this.expressionExecutor = Preconditions.checkNotNull(executor);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, InitVarContext outputChain) throws ExecutionException {


        Preconditions.checkNotNull(outputChain.getScriptContext(), "output is null");

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            Value variableValue = outputChain.getScriptContext().systemStack().current().popResult();

            outputChain.setVariableValue(variableValue);

            return true;
        }

        return false;
    }
}
