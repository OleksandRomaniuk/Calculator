package src.util;


import fsm.CharSequenceReader;
import src.runtime.ScriptContext;

/**
 * {@code ScriptElementExecutor} is a functional interface that can be used to
 * implement any code that can execute program on BazaScript language and
 * put result of execution to {@link ScriptContext}.
 */

@FunctionalInterface
public interface ScriptElementExecutor {

    boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException;
}
