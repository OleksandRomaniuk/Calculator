package src;

import com.google.common.base.Preconditions;
import src.runtime.ScriptContext;
import src.util.WithContext;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionHolderWithContext implements WithContext {

    private final ScriptContext scriptContext;
    private final List<Double> arguments;
    private String functionName;

    public FunctionHolderWithContext(ScriptContext scriptContext) {
        this.scriptContext = scriptContext;
        arguments = new ArrayList<>();
    }

    @Override
    public ScriptContext getContext() {
        return scriptContext;
    }

    public void setArgument(Double argument) {

        arguments.add(argument);
    }

    public String getFunctionName() {
        return functionName;
    }

    public void setFunctionName(String name) {

        this.functionName = Preconditions.checkNotNull(name);
    }

    public List<Double> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

}
