package src.procedure;


import src.runtime.ScriptContext;
import src.type.Value;

import java.util.List;

/**
 * {@code Procedure} is a functional interface that can be used to
 * implement any block of code that can be used as procedure for BazaScript programming language.
 * {@code Procedure} must process {@link List<Double>}
 * and put the result of processing to {@link ScriptContext}.
 */

@FunctionalInterface
public interface Procedure {

    void execute(List<Value> arguments, ScriptContext output);
}
