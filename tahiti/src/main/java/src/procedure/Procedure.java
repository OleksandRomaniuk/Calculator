package src.procedure;



import src.runtime.ScriptContext;

import java.util.List;

public interface Procedure {

    void execute(List<Double> arguments, ScriptContext output);
}
