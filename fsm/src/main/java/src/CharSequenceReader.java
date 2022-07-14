package src;

import com.google.common.base.Preconditions;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;

/**
 * {@code src.CharSequenceReader} is a class which can be used to
 * simple work with char array and have a position of reading.
 */

public final class CharSequenceReader {

    private final char[] source;

    private int readingPosition;

    private final Deque<Integer> savedPositions = new ArrayDeque<>();

    private final int savedPosition = -1;

    public CharSequenceReader(String source) {
        this.source = Preconditions.checkNotNull(source).toCharArray();
    }

    public char read() {
        return source[readingPosition];
    }

    public String readOperator() {

        StringBuilder operator = new StringBuilder();

        int startPosition = position();

        while (canRead() && isOperator(read())) {
            operator.append(read());
            incrementPosition();
        }

        if (startPosition != position())
            decrementPosition();

        return operator.toString();
    }

    public boolean isOperator(char sign) {

        List<Character> operators = List.of('+', '-', '>', '<', '=', '%', '^', '*', '/', '&', '|');

        return operators.contains(sign);
    }

    public void incrementPosition() {

        readingPosition++;
    }

    public char previous() {
        return source[readingPosition - 1];
    }

    public void decrementPosition() {

        readingPosition--;
    }

    public int position() {
        return readingPosition;
    }

    public void setPosition(int newPosition){
        readingPosition = newPosition;
    }

    public boolean canRead() {

        return readingPosition < source.length;
    }

    @Override
    public String toString() {
        return Arrays.toString(source);
    }

    void skipWhitespaces() {

        while (canRead() && Character.isWhitespace(read())) {

            incrementPosition();
        }
    }

    public void savePosition() {

        savedPositions.push(readingPosition);
    }

    public void restorePosition() {

        readingPosition = savedPositions.pop();
    }
}
