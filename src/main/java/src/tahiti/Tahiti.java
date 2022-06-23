package src.tahiti;

import com.google.common.base.Preconditions;
import src.calculator.ExpressionException;
import src.calculator.impl.fsm.util.ResolvingException;
import src.fsm.Input;
import src.tahiti.execute.InterpreterMachine;


public class Tahiti {

    public ProgramResult interpret(ProgramInput code) throws ExpressionException {

        Preconditions.checkNotNull(code);

        Input inputChain = new Input(code.getValue());

        VariableHolder outputChain = new VariableHolder();

        InterpreterMachine interpreterMachine = InterpreterMachine.create();

        try {
            if (!interpreterMachine.run(inputChain, outputChain)) {

                raiseException(inputChain);
            }
        } catch (ResolvingException | ExpressionException e) {
            raiseException(inputChain);
        }

        return new ProgramResult(outputChain.getVariableData());
    }

    private static void raiseException(Input inputChain) throws ExpressionException {
        throw new ExpressionException("Syntax error", inputChain.position());
    }
}
