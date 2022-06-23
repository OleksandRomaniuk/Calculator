package src.tahiti.execute;


import src.calculator.impl.fsm.util.ResolvingException;
import src.fsm.Input;
import src.fsm.Transducer;
import src.tahiti.VariableHolder;
import src.tahiti.program.ProgramMachine;

public class ProgramTransducer implements Transducer<VariableHolder> {
    @Override
    public boolean doTransition(Input inputChain, VariableHolder outputChain) throws ResolvingException {

        ProgramMachine programMachine = ProgramMachine.create();

        if (programMachine.run(inputChain, outputChain)){

            return true;
        }
        return false;
    }
}
