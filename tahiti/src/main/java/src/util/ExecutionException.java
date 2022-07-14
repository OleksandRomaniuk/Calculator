package src.util;

import java.io.Serial;

/**
 *
 */

public class ExecutionException extends Exception {

    @Serial
    private static final long serialVersionUID = 4222650722210941532L;

    public ExecutionException(String message) {
        super(message);
    }
}
