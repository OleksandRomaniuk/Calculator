package src.program;

import com.google.common.base.Preconditions;

public class Compiler {

    public ProgramResult interpret(TahitiProgram code){

        Preconditions.checkNotNull(code);

        CompilerMachine interpreterMachine = new CompilerMachine();

        interpreterMachine.run();

        return null;
    }
}