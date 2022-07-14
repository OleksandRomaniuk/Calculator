package src.executors;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import src.procedure.ProcedureFactory;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.FunctionHolderWithContext;
import src.util.ScriptElementExecutor;


public class ProcedureFactoryExecutor<I> implements ScriptElementExecutor {

    private final FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine;

    public ProcedureFactoryExecutor(FiniteStateMachine<I, FunctionHolderWithContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }


    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, output);

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
