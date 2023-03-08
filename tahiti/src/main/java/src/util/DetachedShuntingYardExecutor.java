package src.util;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.runtime.ProgramContext;
import src.type.Value;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElementExecutor;


/**
 * Implementation of {@link ProgramElementExecutor}
 * that used to run {@link FiniteStateMachine} which work on detached {@link src.calucator.fsm.function.ShuntingYard}.
 */

public class DetachedShuntingYardExecutor<I> implements ProgramElementExecutor {

    private final FiniteStateMachine<I, ProgramContext, ExecutionException> machine;

    public DetachedShuntingYardExecutor(FiniteStateMachine<I, ProgramContext, ExecutionException> machine) {
        this.machine = Preconditions.checkNotNull(machine);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {


        if (!output.isParseOnly()) {
            output.systemStack().create();
        }

        if (machine.run(inputChain, output)) {

            if (output.isParseOnly()) {
                return true;
            }

            Value peekResult = output.systemStack().close().result();

            output.systemStack().current().pushOperand(peekResult);

            return true;
        }

        if (!output.isParseOnly()) {
            output.systemStack().close();
        }

        return false;
    }
}