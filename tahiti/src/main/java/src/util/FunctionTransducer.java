package src.util;



import fsm.CharSequenceReader;
import fsm.Transducer;
import fsm.type.Value;
import src.runtime.WithContext;

import java.util.function.BiConsumer;

public class FunctionTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final BiConsumer<O, Value> consumer;

    private final ScriptElementExecutorFactory factory;

    private final ScriptElement scriptElement;

    public FunctionTransducer(BiConsumer<O, Value> consumer, ScriptElementExecutorFactory factory, ScriptElement scriptElement) {
        this.consumer = consumer;
        this.factory = factory;
        this.scriptElement = scriptElement;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        ScriptElementExecutor expressionExecutor = factory.create(scriptElement);

        if (expressionExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.getScriptContext().isParseonly()){
                return true;
            }

            Value result = outputChain.getScriptContext().systemStack().current().peekResult();

            consumer.accept(outputChain, result);

            return true;
        }

        return false;
    }
}
