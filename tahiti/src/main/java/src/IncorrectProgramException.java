package src;

import com.google.common.base.Preconditions;

public class IncorrectProgramException extends Exception{

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
