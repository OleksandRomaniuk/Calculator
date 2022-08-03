package src.programFeatures.varopertor;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.WithContext;
import src.type.Value;
import src.tahiti.*;

import java.util.function.BiConsumer;

/**
 * Implementation of {@link Transducer} that can take and put result of execution
 * to any output chain that implements {@link WithContext}.
 */

class VariableExpressionTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final ProgramFactory factory;

    private final BiConsumer<O, Value> setVariableValue;

    VariableExpressionTransducer(ProgramFactory factory, BiConsumer<O, Value> setVariableValue) {
        this.factory = Preconditions.checkNotNull(factory);
        this.setVariableValue = Preconditions.checkNotNull(setVariableValue);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        outputChain.getScriptContext().systemStack().create();

        if (factory.create(ProgramElement.EXPRESSION).execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            setVariableValue.accept(outputChain, outputChain.getScriptContext().systemStack().close().result());

            return true;
        }

        outputChain.getScriptContext().systemStack().close();

        return false;
    }
}
