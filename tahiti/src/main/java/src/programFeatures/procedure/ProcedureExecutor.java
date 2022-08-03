package src.programFeatures.procedure;

import com.google.common.base.Preconditions;

import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.util.FunctionHolderWithContext;
import src.tahiti.ProgramElementExecutor;

/**
 * Implementation of {@link ProgramElementExecutor}
 * that used to run {@link FiniteStateMachine} for parsing procedures
 *
 */

public class ProcedureExecutor<I> implements ProgramElementExecutor {

    private final FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine;

    public ProcedureExecutor(FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        FunctionHolderWithContext functionHolder = new FunctionHolderWithContext(output);

        ProcedureFactory procedureFactory = new ProcedureFactory();

        if (machine.run(inputChain, functionHolder)) {

                procedureFactory.create(functionHolder.getFunctionName())
                        .execute(functionHolder.getArguments(), functionHolder.getScriptContext());

            return true;
        }

        return false;

    }
}
