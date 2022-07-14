package src.runtime;

/**
 * {@code WithContext} is a functional interface that can be used for
 * implementations of output chain that needs access to {@link ScriptContext}.
 */

public interface WithContext {

    ScriptContext getScriptContext();

    boolean isParseOnly();
}
