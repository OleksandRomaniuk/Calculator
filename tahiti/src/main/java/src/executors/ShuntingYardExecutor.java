package src.executors;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import fsm.type.Value;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ScriptElementExecutor;

public class ShuntingYardExecutor<I> implements ScriptElementExecutor {

    private final FiniteStateMachine<I, ScriptContext, ExecutionException> machine;

    public ShuntingYardExecutor(FiniteStateMachine<I, ScriptContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        output.systemStack().create();

        if (machine.run(inputChain, output)) {

            if(output.isParseonly()){
                return true;
            }

            Value peekResult = output.systemStack().close().peekResult();

            output.systemStack().current().pushOperand(peekResult);

            return true;
        }

        return false;
    }
}