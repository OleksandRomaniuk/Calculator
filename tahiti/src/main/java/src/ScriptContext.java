package src;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class ScriptContext {

    private final SystemStack systemStack = new SystemStack();

    private final Memory memory = new Memory();

    private final OutputStream outputStream = new ByteArrayOutputStream();

    private final PrintStream printStream = new PrintStream(outputStream);

    public Memory memory(){

        return memory;
    }

    public SystemStack systemStack() {

        return systemStack;
    }

    public PrintStream output() {
        return printStream;
    }
}
