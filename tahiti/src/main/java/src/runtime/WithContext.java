package src.runtime;



public interface WithContext {

    ScriptContext getScriptContext();

    boolean isParseOnly();
}
