package src;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.ResolvingException;
import src.execute.InterpreterMachine;
import src.runtime.ScriptContext;
import src.util.ScriptElementExecutorFactory;


public class Tahiti {

    private static void raiseException(CharSequenceReader inputChain) throws IncorrectProgramException {
        throw new IncorrectProgramException("Syntax error", inputChain.position());
    }

    public ProgramResult interpret(InputProgram code) throws IncorrectProgramException {

        Preconditions.checkNotNull(code);

        CharSequenceReader inputChain = new CharSequenceReader(code.getValue());

        ScriptContext scriptContext = new ScriptContext();

        ScriptElementExecutorFactory factory = new ScriptElementExecutorFactoryImpl();

        InterpreterMachine interpreterMachine = InterpreterMachine.create(factory);

        try {
            if (!interpreterMachine.run(inputChain, scriptContext)) {

                raiseException(inputChain);
            }
        } catch (ResolvingException | IncorrectProgramException e) {
            raiseException(inputChain);
        }

        return new ProgramResult(scriptContext.getOutput().content());
    }
}
