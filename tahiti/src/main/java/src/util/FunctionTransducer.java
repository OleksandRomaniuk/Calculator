package src.util;

import src.CharSequenceReader;
import src.Transducer;
import src.runtime.WithContext;
import src.type.Value;

import java.util.function.BiConsumer;

public class FunctionTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final BiConsumer<O, Value> consumer;

    private final ProgramFactory factory;

    private final ProgramElement programElement;

    public FunctionTransducer(BiConsumer<O, Value> consumer, ProgramFactory factory, ProgramElement programElement) {
        this.consumer = consumer;
        this.factory = factory;
        this.programElement = programElement;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        ProgramElementExecutor expressionExecutor = factory.create(programElement);

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.getScriptContext().isParseOnly()) {
                return true;
            }

            Value result = outputChain.getScriptContext().systemStack().current().popResult();

            consumer.accept(outputChain, result);

            return true;
        }

        return false;
    }
}
