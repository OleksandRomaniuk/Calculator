package src.procedure;

import com.google.common.base.Preconditions;

import java.util.HashMap;
import java.util.Map;

public class ProcedureFactory {

    private final Map<String, Procedure> procedures = new HashMap<>();

    public ProcedureFactory() {

        procedures.put("print", (arguments, output) -> {
            if (!output.isParseOnly()) {
                output.getOutput().print(arguments.toString());
            }
        });

        procedures.put("clear", (arguments, output) -> {
            if (!output.isParseOnly()) {
                output.memory().clearMemory();
            }
        });

    }

    public Procedure create(String procedureName) {

        Preconditions.checkNotNull(procedureName);

        return procedures.get(procedureName);
    }
}
