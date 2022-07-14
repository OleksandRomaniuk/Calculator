package src;

import com.google.common.base.Preconditions;
import src.execute.InterpreterMachine;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ProgramFactory;


public class Tahiti {


    public ProgramResult interpret(InputProgram code) throws IncorrectProgramException {

        Preconditions.checkNotNull(code);

        CharSequenceReader inputChain = new CharSequenceReader(code.getValue());

        ScriptContext scriptContext = new ScriptContext();

        ProgramFactory factory = new ProgramExecutorFactoryImpl();

        InterpreterMachine interpreterMachine = InterpreterMachine.create(Preconditions.checkNotNull(factory), errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        try {
            if (!interpreterMachine.run(inputChain, scriptContext)) {

                programExeption(inputChain);
            }
        } catch (IncorrectProgramException | ExecutionException e) {
            programExeption(inputChain);
        }

        return new ProgramResult(scriptContext.getOutput().content());
    }
    private static void programExeption(CharSequenceReader inputChain) throws IncorrectProgramException {
        throw new IncorrectProgramException("Syntax error", inputChain.position());
    }

}
