package src.tahiti.initvar;

import src.calculator.impl.fsm.function.name.IdentifierMachine;
import src.calculator.impl.fsm.util.ResolvingException;
import src.fsm.Input;
import src.fsm.Transducer;
import src.tahiti.VariableHolder;

public class variableNameTransducer implements Transducer<VariableHolder> {
    @Override
    public boolean doTransition(Input inputChain, VariableHolder outputChain) throws ResolvingException {
        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){

            outputChain.putVariable(stringBuilder.toString(), 0.0);

            outputChain.setLastVariableName(stringBuilder.toString());
            return true;
        }

        return false;
    }
}
