package src;

import com.google.common.base.Preconditions;

import java.util.*;

/**
 * CharSequenceReader is a class for work with char array.
 */

public final class CharSequenceReader {

    private final char[] source;

    private int readingPosition;

    public CharSequenceReader(String source) {
        this.source = Preconditions.checkNotNull(source).toCharArray();
    }

    public char read() {
        return source[readingPosition];
    }

    public void incrementPosition() {

        readingPosition++;
    }

    public Optional<String> copyOfRange(int length) {

        var copyResult = new StringBuilder();

        if (source.length - position() < length) {

            return Optional.empty();
        }

        for (var i = 0; i <= length - 1; i++) {

            copyResult.append(read());

            incrementPosition();
        }

        return Optional.of(copyResult.toString());
    }

    public int position() {
        return readingPosition;
    }

    public void setPosition(int newPosition) {
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
}
