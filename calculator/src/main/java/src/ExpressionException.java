package src;

import com.google.common.base.Preconditions;


/**
 * Custom exception for handling wrong symbol's
 * position in evaluated expression
 */


public class ExpressionException extends Exception {

    private final int errorPosition;

    public ExpressionException(String message, int errorPosition) {
        super(Preconditions.checkNotNull(message));

        Preconditions.checkArgument(errorPosition >= 0);
        this.errorPosition = errorPosition;
    }

    public int getErrorPosition() {
        return errorPosition;
    }
}
