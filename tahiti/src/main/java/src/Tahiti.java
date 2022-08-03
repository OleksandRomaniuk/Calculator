package src;

import com.google.common.base.Preconditions;
import src.interpreter.InterpreterMachine;
import src.runtime.ProgramContext;
import src.tahiti.*;


/**
 * An API for interpreting programs on Tahiti language.
 */

public class Tahiti {

    private static void raiseException(CharSequenceReader inputChain) throws IncorrectProgramException {
        throw new IncorrectProgramException("Syntax error", inputChain.position());
    }

    public ProgramResult interpret(InputProgram code) throws IncorrectProgramException {

        Preconditions.checkNotNull(code);

        CharSequenceReader inputChain = new CharSequenceReader(code.getValue());

        ProgramContext programContext = new ProgramContext();

        ProgramFactory factory = new ProgramFactoryImpl();

        InterpreterMachine interpreterMachine = InterpreterMachine.create(Preconditions.checkNotNull(factory), errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        try {
            if (!interpreterMachine.run(inputChain, programContext)) {

                raiseException(inputChain);
            }
        } catch (IncorrectProgramException | ExecutionException e) {
            raiseException(inputChain);
        }

        return new ProgramResult(programContext.getOutput().content());
    }
}
