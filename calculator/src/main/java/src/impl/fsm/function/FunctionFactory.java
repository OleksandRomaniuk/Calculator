package src.impl.fsm.function;

import com.google.common.base.Preconditions;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

/**
 * {@code FunctionFactory} is a realization of factory pattern
 * that create an instance of {@link Function} by name.
 */

public class FunctionFactory {

    private final Map<String, Function> functions = new TreeMap<>();

    public FunctionFactory() {

        functions.put("min", arguments -> {
            Preconditions.checkState(arguments.size()>1, "Not enough arguments in min function");

            return arguments.stream().min(Double::compare).get();
        });

        functions.put("max", arguments -> {
            Preconditions.checkState(arguments.size()>1, "Not enough arguments in max function");

            return arguments.stream().max(Double::compare).get();
        });

        functions.put("avg", arguments -> {
            Preconditions.checkState(arguments.size()>1, "Not enough arguments in avg function");

            return arguments.stream().collect(Collectors.averagingDouble((a) -> a));
        });
        functions.put("sqrt", arguments -> {
            Preconditions.checkState(arguments.size() == 1);

            return StrictMath.sqrt(arguments.get(0));
        });
        functions.put("pi", arguments -> {
            Preconditions.checkState(arguments.isEmpty());
            return Math.PI;
        });
    }

    public Function create(String functionName){

        return functions.get(functionName);
    }

    public boolean hasFunction(String name){

        return functions.containsKey(name);
    }
}
