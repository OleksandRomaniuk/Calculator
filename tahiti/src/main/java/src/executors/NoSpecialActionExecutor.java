package src.executors;


import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import fsm.ResolvingException;
import src.runtime.ScriptContext;
import src.util.ScriptElementExecutor;

public class NoSpecialActionExecutor<I> implements ScriptElementExecutor {

    private final FiniteStateMachine<I, ScriptContext> machine;

    public NoSpecialActionExecutor(FiniteStateMachine<I, ScriptContext> machine) {
        this.machine = machine;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException {

        return machine.run(inputChain, output);
    }
}
