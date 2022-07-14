package src.executors;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ProgramElementExecutor;

public class NoSpecialActionExecutor<I> implements ProgramElementExecutor {

    private final FiniteStateMachine<I, ScriptContext, ExecutionException> machine;

    public NoSpecialActionExecutor(FiniteStateMachine<I, ScriptContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, output);

        return machine.run(inputChain, output);
    }
}
