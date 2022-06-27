package src.operand;


import src.fsm.Input;
import src.fsm.Transducer;
import src.ProgramMemory;
import src.impl.fsm.function.name.IdentifierMachine;
import src.impl.fsm.util.ResolvingException;

public class VariableTransducer implements Transducer<ProgramMemory> {

    @Override
    public boolean doTransition(Input inputChain, ProgramMemory outputChain) throws ResolvingException {
        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){

            if (outputChain.hasVariable(stringBuilder.toString())){

                outputChain.pushOperand(outputChain.getVariable(stringBuilder.toString()));
            }

            return true;
        }

        return false;
    }
}
