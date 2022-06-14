package src.impl.fsm.function;

import src.impl.fsm.Function;

import java.util.Map;
import java.util.TreeMap;

public class FunctionFactory {

    private final Map<String, Function> functions = new TreeMap<>();

    public FunctionFactory() {

        functions.put("min", arguments -> {
            double min = arguments.get(0);

            for (double argument : arguments) {

                if (argument < min) {
                    min = argument;
                }
            }

            return min;
        });

        functions.put("max", arguments -> {
            double max = arguments.get(0);

            for (double argument : arguments) {

                if (argument > max) {
                    max = argument;
                }
            }

            return max;
        });

        functions.put("avg", arguments -> {
            double avg=0;

            for (double argument : arguments) {
                avg+=argument;
            }
            avg = avg/ arguments.size();
            return avg;
        });
        functions.put("pi", arguments -> {
            return Math.PI;
        });
        functions.put("sum", arguments -> {
            double avg=0;

            for (double argument : arguments) {
                avg+=argument;
            }
            avg = avg/ arguments.size();
            return avg;
        });
        functions.put("pi", arguments -> {
            double sum = 0;

            for (double argument : arguments) {
                sum += argument;
            }

            return sum;
        });
    }


    public Function create(String functionName){

        return functions.get(functionName);
    }
}
