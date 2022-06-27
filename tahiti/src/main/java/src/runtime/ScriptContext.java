package src.runtime;


import src.util.WithContext;

public class ScriptContext implements WithContext {

    private final SystemStack systemStack = new SystemStack();

    private final Memory memory = new Memory();

    private final Output output = new Output();

    public ScriptContext() {
        systemStack.create();
    }

    public Memory memory() {

        return memory;
    }

    public SystemStack systemStack() {

        return systemStack;
    }

    public Output getOutput() {
        return output;
    }

    @Override
    public ScriptContext getContext() {
        return this;
    }

    public boolean hasVariable(String variableName) {
        return memory.hasVariable(variableName);
    }
}
