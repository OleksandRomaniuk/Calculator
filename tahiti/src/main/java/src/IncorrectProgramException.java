package src;

import com.google.common.base.Preconditions;

import java.io.Serial;

/**
 * Throws that {@link Tahiti} need to execute.
 */

public class IncorrectProgramException extends Exception {

    @Serial
    private static final long serialVersionUID = -540935324999045017L;
    private final int errorPosition;

    public IncorrectProgramException(String message, int errorPosition) {

        Preconditions.checkNotNull(message);

        Preconditions.checkArgument(errorPosition >= 0);

        this.errorPosition = errorPosition;
    }

    public int getErrorPosition() {
        return errorPosition;
    }
}
