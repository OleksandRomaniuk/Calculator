package src.impl.fsm.function;

import src.impl.fsm.Transducer;
import src.impl.fsm.function.name.IdentifierMachine;
import src.impl.fsm.util.Input;
import src.impl.fsm.util.FunctionHolder;
import src.impl.fsm.util.ResolvingException;

/**
 *
 * Implementation of {@link Transducer} for {@link StringBuilder} output.
 *
 */

class FunctionNameTransducer implements Transducer<FunctionHolder> {

    @Override
    public boolean doTransition(Input inputChain, FunctionHolder outputChain) throws ResolvingException {

        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){
            outputChain.setFunctionName(stringBuilder.toString());
            return true;
        }
        return false;
    }

}
