package src.executors;


import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import fsm.ResolvingException;
import src.FunctionHolderWithContext;

import src.fsm.function.FunctionFactory;
import src.runtime.ScriptContext;
import src.util.ScriptElementExecutor;

public class FunctionFactoryExecutor<I> implements ScriptElementExecutor {
    private final FiniteStateMachine<I, FunctionHolderWithContext> machine;

    public FunctionFactoryExecutor(FiniteStateMachine<I, FunctionHolderWithContext> machine) {
        this.machine = machine;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException {

        FunctionFactory functionFactory = new FunctionFactory();

        var functionHolder = new FunctionHolderWithContext(output);

        if (machine.run(inputChain, functionHolder)) {

            Double evaluateFunctionResult = functionFactory.
                    create(functionHolder.getFunctionName()).evaluate(functionHolder.getArguments());

            output.systemStack().current().pushOperand(evaluateFunctionResult);

            return true;
        }

        return false;
    }
}
