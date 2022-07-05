package src;

import com.google.common.base.Preconditions;
import fsm.type.Value;
import src.runtime.ScriptContext;
import src.util.WithContext;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;




public class FunctionHolderWithContext implements WithContext {

    private final ScriptContext scriptContext;
    private final List<Value> arguments;
    private String functionName;

    public FunctionHolderWithContext(ScriptContext scriptContext) {
        this.scriptContext = Preconditions.checkNotNull(scriptContext);
        arguments = new ArrayList<>();
    }

    @Override
    public ScriptContext getScriptContext() {
        return scriptContext;
    }

    @Override
    public boolean isParseonly() {
        return scriptContext.isParseonly();
    }

    void setArgument(Value argument) {

        arguments.add(argument);
    }

    public String getFunctionName() {
        return functionName;
    }

    void setFunctionName(String name) {

        this.functionName = Preconditions.checkNotNull(name);
    }

    public List<Value> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

}
