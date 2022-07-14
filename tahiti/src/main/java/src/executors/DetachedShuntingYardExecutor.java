package src.executors;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.runtime.ScriptContext;
import src.type.Value;
import src.util.ExecutionException;
import src.util.ProgramElementExecutor;


public class DetachedShuntingYardExecutor<I> implements ProgramElementExecutor {

    private final FiniteStateMachine<I, ScriptContext, ExecutionException> machine;

    public DetachedShuntingYardExecutor(FiniteStateMachine<I, ScriptContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {


        if (!output.isParseOnly()) {
            output.systemStack().create();
        }

        if (machine.run(inputChain, output)) {

            if (output.isParseOnly()) {
                return true;
            }

            Value peekResult = output.systemStack().close().popResult();

            output.systemStack().current().pushOperand(peekResult);

            return true;
        }

        return false;
    }
}