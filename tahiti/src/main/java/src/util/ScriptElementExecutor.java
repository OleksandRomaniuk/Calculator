package src.util;


import src.CharSequenceReader;
import src.runtime.ScriptContext;


@FunctionalInterface
public interface ScriptElementExecutor {

    boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException;
}
