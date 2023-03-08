package src.runtime;

import com.google.common.base.Preconditions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.type.Value;

import java.util.HashMap;
import java.util.Map;

/**
 * Class that used to store variables
 * Memory has savedVariables that used to store variables as main memory.
 * SavedVariables was intended for possibility of memory rollback.
 */

public class Memory {

    private static final Logger logger = LoggerFactory.getLogger(Memory.class);

    private final Map<String, Value> variables = new HashMap<>();

    private final Map<String, Value> savedVariables = new HashMap<>();

    public void setVariable(String identifier, Value value) {

        Preconditions.checkNotNull(identifier, value);

        variables.put(identifier, value);

        savedVariables.put(identifier, value);
    }

    public void restore() {

        variables.putAll(savedVariables);
    }

    public void backup() {

        savedVariables.putAll(variables);
    }

    public void clearCache() {

        savedVariables.clear();
    }

    public Value getVariableValueFromCache(String identifier) {

        return savedVariables.get(Preconditions.checkNotNull(identifier));
    }

    boolean hasVariable(String variableName) {

        return variables.containsKey(Preconditions.checkNotNull(variableName));
    }

    public void setVariableToCache(String identifier, Value value) {

        Preconditions.checkNotNull(identifier, value);

        savedVariables.put(identifier, value);
    }

}
