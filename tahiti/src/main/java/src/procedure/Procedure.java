package src.procedure;


import src.runtime.ScriptContext;
import src.type.Value;

import java.util.List;



@FunctionalInterface
public interface Procedure {

    void execute(List<Value> arguments, ScriptContext output);
}
