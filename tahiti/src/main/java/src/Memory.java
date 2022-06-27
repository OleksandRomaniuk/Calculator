package src;

import java.util.HashMap;
import java.util.Map;

public class Memory {

    private final Map<String, Double> variables = new HashMap<>();

    public void setVariable(String identifier, Double value){

        variables.put(identifier, value);
    }

    public Double getVariable(String identifier){

        return variables.get(identifier);
    }
}
