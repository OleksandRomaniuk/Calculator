package src.runtime;

/**
 * WithContext is a functional interface
 */

public interface WithContext {

    ScriptContext getScriptContext();

    boolean isParseOnly();
}
