package src.programFeatures.procedure;

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

    }

    public Procedure create(String procedureName) {

        Preconditions.checkNotNull(procedureName);

        return procedures.get(procedureName);
    }

}
