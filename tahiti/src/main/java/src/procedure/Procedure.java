package src.procedure;



import fsm.type.Value;
import src.runtime.ScriptContext;

import java.util.List;



@FunctionalInterface
public interface Procedure {

    void execute(List<Value> arguments, ScriptContext output);
}
