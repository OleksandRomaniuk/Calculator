package src.util;


import fsm.CharSequenceReader;
import src.runtime.ScriptContext;


@FunctionalInterface
public interface ScriptElementExecutor {

    boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException;
}
