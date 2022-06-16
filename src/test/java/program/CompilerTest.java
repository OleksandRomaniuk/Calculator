package program;

import org.junit.jupiter.api.Test;
import src.program.CompilerMachine;
import src.program.DevchicProgram;
import src.program.ProgramResult;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CompilerTest {

    @Test
    public void initializeVariableTest() {

        DevchicProgram program = new DevchicProgram("a=5;b=a*2;println(a,b)");

        CompilerMachine compilerMachine = new CompilerMachine();

        ProgramResult programResult = new ProgramResult();

        assertEquals("2", programResult.getValue());
    }
}