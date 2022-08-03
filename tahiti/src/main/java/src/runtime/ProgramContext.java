package src.runtime;

/**
 * Class that used as a memory for interpreting program code.
 */

public class ProgramContext {

    private final SystemStack systemStack = new SystemStack();

    private final Memory memory = new Memory();

    private final Output output = new Output();

    private boolean parsingPermission;

    public ProgramContext() {
        systemStack.create();
    }

    public Memory memory() {

        return memory;
    }

    public void setParsingPermission(boolean parsingPermission) {
        this.parsingPermission = parsingPermission;
    }

    public SystemStack systemStack() {

        return systemStack;
    }

    public Output getOutput() {
        return output;
    }

    public boolean isParseOnly() {
        return parsingPermission;
    }

    public boolean hasVariable(String variableName) {
        return memory.hasVariable(variableName);
    }
}
