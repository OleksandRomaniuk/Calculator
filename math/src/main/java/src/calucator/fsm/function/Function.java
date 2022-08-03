package src.calucator.fsm.function;


import src.type.Value;

import java.util.List;

/**
 * Interface that can be used to implement any block of code
 */

public interface Function{

    Value evaluate(List<Value> arguments);
}
