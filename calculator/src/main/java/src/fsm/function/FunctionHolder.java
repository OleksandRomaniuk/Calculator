package src.fsm.function;

import com.google.common.base.Preconditions;
import fsm.type.Value;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class FunctionHolder {

    private String functionName;
    private final List<Value> arguments;

    public FunctionHolder() {
        arguments = new ArrayList<>();
    }

    public void setFunctionName(String name){

        this.functionName = Preconditions.checkNotNull(name);
    }

    public void setArgument(Value argument){

        arguments.add(argument);
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Value> getArguments() {
        return Collections.unmodifiableList(arguments);
    }
}
