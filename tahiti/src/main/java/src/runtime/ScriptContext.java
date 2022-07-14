package src.runtime;

public class ScriptContext implements WithContext {

    private final SystemStack systemStack = new SystemStack();

    private final Memory memory = new Memory();

    private final Output output = new Output();

    private boolean parsingPermission;

    public ScriptContext() {
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

    @Override
    public ScriptContext getScriptContext() {
        return this;
    }

    @Override
    public boolean isParseOnly() {
        return parsingPermission;
    }

    public boolean hasVariable(String variableName) {
        return memory.hasVariable(variableName);
    }
}
