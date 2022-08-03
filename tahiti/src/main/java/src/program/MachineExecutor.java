package src.program;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElementExecutor;

/**
 * Implementation of {@link ProgramElementExecutor} that used to run any type of {@link FiniteStateMachine}.
 */

public class MachineExecutor<I> implements ProgramElementExecutor {

    private final FiniteStateMachine<I, ProgramContext, ExecutionException> machine;

    public MachineExecutor(FiniteStateMachine<I, ProgramContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        Preconditions.checkNotNull(inputChain, output);

        return machine.run(inputChain, output);
    }
}
