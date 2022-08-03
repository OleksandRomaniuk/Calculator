package src.calucator.fsm.function;

import com.google.common.base.Preconditions;
import src.type.DoubleValue;
import src.type.DoubleValueVisitor;
import src.type.Value;

import java.util.Collection;
import java.util.List;
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
            Preconditions.checkState(arguments.size() > 1, "Not enough arguments in min function");

            List<Double> doubles = toDouble(arguments);

            return new DoubleValue(doubles.stream().min(Double::compare).get());
        });

        functions.put("max", arguments -> {
            Preconditions.checkState(arguments.size() > 1, "Not enough arguments in max function");

            List<Double> doubles = toDouble(arguments);

            return new DoubleValue(doubles.stream().max(Double::compare).get());
        });

        functions.put("avg", arguments -> {
            Preconditions.checkState(arguments.size() > 1, "Not enough arguments in avg function");

            List<Double> doubles = toDouble(arguments);

            return new DoubleValue(doubles.stream().collect(Collectors.averagingDouble((a) -> a)));

        });
        functions.put("pi", arguments -> {
            Preconditions.checkState(arguments.isEmpty());

            return new DoubleValue(Math.PI);
        });
    }

    private static List<Double> toDouble(Collection<Value> values) {
        return values.stream().mapToDouble(DoubleValueVisitor::read).boxed().collect(Collectors.toList());
    }

    public Function create(String functionName) {

        return functions.get(functionName);
    }

}
