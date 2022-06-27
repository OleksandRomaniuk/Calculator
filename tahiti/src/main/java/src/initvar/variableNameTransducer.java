package src.initvar;


import src.fsm.Input;
import src.fsm.Transducer;
import src.impl.fsm.function.name.IdentifierMachine;
import src.impl.fsm.util.ResolvingException;

public class variableNameTransducer implements Transducer<InitVarContext> {
    @Override
    public boolean doTransition(Input inputChain, InitVarContext outputChain) throws ResolvingException {
        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){



            outputChain.setVariableName(stringBuilder.toString());

            return true;
        }

        return false;
    }
}
