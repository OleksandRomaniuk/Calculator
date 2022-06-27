package src.executors;


import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import fsm.ResolvingException;
import src.FunctionHolderWithContext;
import src.procedure.ProcedureFactory;
import src.runtime.ScriptContext;
import src.util.ScriptElementExecutor;

public class ProcedureFactoryExecutor<I> implements ScriptElementExecutor {

    private final FiniteStateMachine<I, FunctionHolderWithContext> machine;

    public ProcedureFactoryExecutor(FiniteStateMachine<I, FunctionHolderWithContext> machine) {
        this.machine = machine;
    }


    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException {

        FunctionHolderWithContext functionHolder = new FunctionHolderWithContext(output);

        ProcedureFactory procedureFactory = new ProcedureFactory();

        if (machine.run(inputChain, functionHolder)) {

            procedureFactory.create(functionHolder.getFunctionName())
                    .execute(functionHolder.getArguments(), functionHolder.getContext());

            return true;
        }

        return false;

    }
}
