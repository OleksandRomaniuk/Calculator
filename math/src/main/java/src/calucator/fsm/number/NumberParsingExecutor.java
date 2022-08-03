package src.calucator.fsm.number;


import src.CharSequenceReader;
import src.ExceptionThrower;
import src.runtime.ProgramContext;
import src.type.Value;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElementExecutor;

import java.util.Optional;


/**
 * Implementation of {@link ProgramElementExecutor} that create and run {@link NumberStateMachine}.
 */

public class NumberParsingExecutor implements ProgramElementExecutor {

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        ExceptionThrower<ExecutionException> exceptionThrower = errorMessage -> {
            throw new ExecutionException(errorMessage);
        };

        Optional<Value> execute = NumberStateMachine.execute(inputChain, exceptionThrower);

        if (execute.isPresent()) {

            if (!output.isParseOnly()) {

                output.systemStack().current().pushOperand(execute.get());
            }

            return true;
        }

        return false;
    }
}
