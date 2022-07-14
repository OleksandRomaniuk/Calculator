package src.util;


import src.CharSequenceReader;
import src.runtime.ScriptContext;


@FunctionalInterface
public interface ProgramElementExecutor {

    boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException;
}
