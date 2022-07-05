package fsm;

import com.google.common.base.Preconditions;

import java.util.Arrays;

/**
 * СharSequenceReader is a class which can be used to
 * simple work with char array and have a position of reading.
 */

public final class CharSequenceReader {

    private final char[] source;

    private int readingPosition;

    private int savedPosition = -1;

    public CharSequenceReader(String source) {
        this.source = Preconditions.checkNotNull(source).toCharArray();
    }

    public char read() {
        return source[readingPosition];
    }

    public String readOperator(){

        var operator = new StringBuilder();

        if (read() == '>' || read() == '<'){
            operator.append(read());
            incrementPosition();
            if (read() == '='){
                operator.append(read());
            }
            else decrementPosition();

        }
        else operator.append(read());

        return operator.toString();
    }

    public void incrementPosition() {

        readingPosition++;
    }

    public char previous(){
        return source[readingPosition-1];
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

        while (canRead() && Character.isWhitespace(read())){

                incrementPosition();
        }
    }


}
