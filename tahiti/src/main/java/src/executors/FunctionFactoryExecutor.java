package src.executors;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.FunctionHolderWithContext;
import src.fsm.function.FunctionFactory;
import src.runtime.ScriptContext;
import src.type.Value;
import src.util.ExecutionException;
import src.util.ScriptElementExecutor;


public class FunctionFactoryExecutor<I> implements ScriptElementExecutor {
    private final FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine;

    public FunctionFactoryExecutor(FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, output);

        FunctionFactory functionFactory = new FunctionFactory();

        var functionHolder = new FunctionHolderWithContext(output);

        if (machine.run(inputChain, functionHolder)) {

            if (output.isParseOnly()) {
                return true;
            }

            Value evaluateFunctionResult = functionFactory.
                    create(functionHolder.getFunctionName()).evaluate(functionHolder.getArguments());

            output.systemStack().current().pushOperand(evaluateFunctionResult);

            return true;
        }

        return false;
    }
}
