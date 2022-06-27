package src.util;


import fsm.CharSequenceReader;
import fsm.ResolvingException;
import src.runtime.ScriptContext;

@FunctionalInterface
public interface ScriptElementExecutor {

    boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ResolvingException;
}
