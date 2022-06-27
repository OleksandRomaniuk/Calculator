package src.runtime;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

public class Output {

    private final OutputStream outputStream = new ByteArrayOutputStream();

    private final PrintStream printStream = new PrintStream(outputStream);

    public PrintStream output() {
        return printStream;
    }

    public String content() {

        return outputStream.toString();
    }

    public void print(String value) {

        printStream.print(value);
    }
}
