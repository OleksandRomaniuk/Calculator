package src.tahiti;

import java.io.Serial;


/**
 * Exception that throws in case of invalid program input.
 */

public class ExecutionException extends Exception {

    @Serial
    private static final long serialVersionUID = 4222650722210941532L;

    public ExecutionException(String message) {
        super(message);
    }
}
