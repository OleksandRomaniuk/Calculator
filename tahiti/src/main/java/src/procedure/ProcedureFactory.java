package src.procedure;

import java.util.HashMap;
import java.util.Map;

public class ProcedureFactory {

    private final Map<String, Procedure> procedures = new HashMap<>();

    public ProcedureFactory() {

        procedures.put("print", (arguments, output) -> output.getOutput().print(arguments.toString()));

    }

    public Procedure create(String procedureName) {

        return procedures.get(procedureName);
    }
}
