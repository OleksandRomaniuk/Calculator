package src.calucator.fsm.function;

import com.google.common.base.Preconditions;
import src.type.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FunctionHolder {

    private final List<Value> arguments;
    private String functionName;

    public FunctionHolder() {
        arguments = new ArrayList<>();
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
