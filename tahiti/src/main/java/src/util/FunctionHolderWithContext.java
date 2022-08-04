package src.util;

import com.google.common.base.Preconditions;
import src.runtime.ProgramContext;
import src.runtime.WithContext;
import src.type.Value;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Simple holder to store data and make an instance of {@link src.calucator.fsm.function.Function}.
 */


public class FunctionHolderWithContext implements WithContext {

    private final ProgramContext programContext;

    private final List<Value> arguments;

    private String functionName;

    public FunctionHolderWithContext(ProgramContext programContext) {

        this.programContext = Preconditions.checkNotNull(programContext);

        arguments = new ArrayList<>();
    }

    @Override
    public ProgramContext getScriptContext() {
        return programContext;
    }

    @Override
    public boolean isParseOnly() {
        return programContext.isParseOnly();
    }

    public void addArgument(Value argument) {

        arguments.add(argument);
    }

    public String FunctionName() {
        return functionName;
    }

    public void FunctionName(String name) {

        this.functionName = Preconditions.checkNotNull(name);
    }

    public List<Value> getArguments() {
        return Collections.unmodifiableList(arguments);
    }

}
