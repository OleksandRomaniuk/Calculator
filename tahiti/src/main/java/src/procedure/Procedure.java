package src.procedure;



import src.ProgramMemory;

import java.util.List;

public interface Procedure {

    void create(List<Double> arguments, ProgramMemory output);
}
