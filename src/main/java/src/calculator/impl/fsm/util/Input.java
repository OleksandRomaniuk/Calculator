package src.calculator.impl.fsm.util;

import com.google.common.base.Preconditions;

import java.util.Arrays;


/**
 *
 * The class that implements the input data
 */
public final class Input {

    private final char[] source;

    private int readingPosition;

    public Input(String source) {
        this.source = Preconditions.checkNotNull(source).toCharArray();
    }

    public char read() {
        return source[readingPosition];
    }

    public void incrementPosition() {

        readingPosition++;
    }

    public int position() {
        return readingPosition;
    }

    public boolean canRead() {

        return readingPosition < source.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(source);
    }

    public void skipWhitespaces() {

        while (canRead() && Character.isWhitespace(read())){

                incrementPosition();
        }
    }
}
