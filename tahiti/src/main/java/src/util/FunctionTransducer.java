package src.util;


import fsm.CharSequenceReader;
import fsm.ResolvingException;
import fsm.Transducer;

import java.util.function.BiConsumer;

public class FunctionTransducer<O extends WithContext> implements Transducer<O> {

    private final BiConsumer<O, Double> consumer;

    private final ScriptElementExecutor expressionExecutor;

    public FunctionTransducer(BiConsumer<O, Double> consumer, ScriptElementExecutor expressionExecutor) {
        this.consumer = consumer;
        this.expressionExecutor = expressionExecutor;
    }


    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ResolvingException {
        if (expressionExecutor.execute(inputChain, outputChain.getContext())) {

            double result = outputChain.getContext().systemStack().current().peekResult();

            consumer.accept(outputChain, result);

            return true;
        }

        return false;
    }
}
