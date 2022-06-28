package src.calculator.impl.fsm.function;


import src.calculator.impl.fsm.function.name.IdentifierMachine;
import src.calculator.impl.fsm.util.Input;
import src.calculator.impl.fsm.util.FunctionHolder;
import src.calculator.impl.fsm.util.ResolvingException;
import src.fsm.Transducer;

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
