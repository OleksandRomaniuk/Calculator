package src.util;

import com.google.common.base.Preconditions;

import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.calucator.fsm.function.FunctionFactory;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.util.FunctionHolderWithContext;
import src.tahiti.ProgramElementExecutor;

/**
 * Implementation of {@link ProgramElementExecutor}
 * that used to run {@link FiniteStateMachine} for parsing math functions.
 */

public class FunctionExecutor<I> implements ProgramElementExecutor {

    private final FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine;

    public FunctionExecutor(FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, output);

        var functionFactory = new FunctionFactory();

        var functionHolder = new FunctionHolderWithContext(output);

        if (machine.run(inputChain, functionHolder)) {

            if (!output.isParseOnly()) {

                var evaluateFunctionResult = functionFactory.
                        create(functionHolder.FunctionName()).evaluate(functionHolder.getArguments());

                output.systemStack().current().pushOperand(evaluateFunctionResult);
            }

            return true;
        }

        return false;
    }
}
