package src.fsm.function;

import java.util.List;

/**
 * {@code Function} is a functional interface that can be used to
 * implement any block of code that solve a {@link List<Double>}
 * and return {@link Double}.
 */

public interface Function{

    Double evaluate(List<Double> arguments);
}
