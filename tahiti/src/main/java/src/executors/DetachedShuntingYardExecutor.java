package src.executors;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import fsm.ResolvingException;
import src.runtime.ScriptContext;
import src.util.ScriptElementExecutor;


public class DetachedShuntingYardExecutor<I> implements ScriptElementExecutor {

    private final FiniteStateMachine<I, ScriptContext> machine;

    public DetachedShuntingYardExecutor(FiniteStateMachine<I, ScriptContext> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException {

        output.systemStack().create();

        if (machine.run(inputChain, output)) {

            double peekResult = output.systemStack().close().peekResult();

            output.systemStack().current().pushOperand(peekResult);

            return true;
        }

        return false;
    }
}
