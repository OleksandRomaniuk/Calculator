package src.util;

import com.google.common.base.Preconditions;
import src.runtime.ScriptContext;
import src.runtime.WithContext;
import src.type.Value;

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
    public boolean isParseOnly() {
        return scriptContext.isParseOnly();
    }

    public void setArgument(Value argument) {

        arguments.add(argument);
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String name) {

        this.functionName = Preconditions.checkNotNull(name);
    }

    public List<Value> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

}
