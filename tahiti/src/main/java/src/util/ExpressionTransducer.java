package src.util;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.WithContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElement;
import src.tahiti.ProgramElementExecutor;
import src.tahiti.ProgramFactory;
import src.type.Value;

import java.util.function.BiConsumer;

/**
 * Implementation of {@link Transducer} for creating and executing ProgramElementExecutor
 * and put result of executing to output chain
 */

public class ExpressionTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final BiConsumer<O, Value> valueConsumer;

    private final ProgramFactory factory;

    private final ProgramElement programElement;

    public ExpressionTransducer(BiConsumer<O, Value> valueConsumer, ProgramFactory factory, ProgramElement programElement) {

        this.valueConsumer = Preconditions.checkNotNull(valueConsumer);

        this.factory = Preconditions.checkNotNull(factory);

        this.programElement = Preconditions.checkNotNull(programElement);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        ProgramElementExecutor expressionExecutor = factory.create(programElement);

        if (!outputChain.isParseOnly()) {

            outputChain.getScriptContext().systemStack().create();
        }

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (!outputChain.getScriptContext().isParseOnly()) {

                Value result = outputChain.getScriptContext().systemStack().close().result();

                valueConsumer.accept(outputChain, result);
            }

            return true;
        }

        if (!outputChain.isParseOnly()) {

            outputChain.getScriptContext().systemStack().close();
        }

        return false;
    }
}
