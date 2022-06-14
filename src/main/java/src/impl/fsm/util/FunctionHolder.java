package src.impl.fsm.util;

import com.google.common.base.Preconditions;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionHolder {

    private String functionName;
    private final List<Double> arguments;

    public FunctionHolder() {
        arguments = new ArrayList<>();
    }

    public void setFunctionName(String name){

        this.functionName = Preconditions.checkNotNull(name);
    }

    public void setArgument(Double argument){

        arguments.add(argument);
    }

    public String getFunctionName() {
        return functionName;
    }

    public List<Double> getArguments() {
        return Collections.unmodifiableList(arguments);
    }
}
