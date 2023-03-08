package src.programFeatures.procedure;



import src.runtime.ProgramContext;
import src.type.Value;

import java.util.List;

@FunctionalInterface
public interface Procedure {

    void execute(List<Value> arguments, ProgramContext output);
}
