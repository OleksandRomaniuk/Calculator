package src.util;

import src.runtime.ScriptContext;

public interface WithContext {

    ScriptContext getScriptContext();

    boolean isParseonly();
}
