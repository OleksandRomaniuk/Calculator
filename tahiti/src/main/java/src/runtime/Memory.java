package src.runtime;

import com.google.common.base.Preconditions;
import fsm.type.Value;


import java.util.HashMap;
import java.util.Map;

/**
 * {@code Memory} is a class that used to store Double type variables.
 */

public class Memory {

    private final Map<String, Value> variables = new HashMap<>();

    public void setVariable(String identifier, Value value) {

        Preconditions.checkNotNull(identifier, value);

        variables.put(identifier, value);
    }

    public Value getVariable(String identifier) {

        return variables.get(Preconditions.checkNotNull(identifier));
    }

    public boolean hasVariable(String variableName) {

        return variables.containsKey(Preconditions.checkNotNull(variableName));
    }

    public void clearMemory() {
        variables.clear();
    }
}
