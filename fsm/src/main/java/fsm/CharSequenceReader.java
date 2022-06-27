package fsm;



import java.util.Arrays;

/**
 * {@code CharSequenceReader} is a class which can be used to
 * simple work with char array and have a position of reading.
 */

public final class CharSequenceReader {

    private final char[] source;

    private int readingPosition;

    private int savedPosition = -1;

    public CharSequenceReader(String source) {
        this.source = source.toCharArray();
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

    void skipWhitespaces() {

        while (canRead() && Character.isWhitespace(read())){

                incrementPosition();
        }
    }

    void savePosition() {

        savedPosition = readingPosition;
    }

    void restorePosition() {

        readingPosition = savedPosition;

        savedPosition = -1;
    }
}
