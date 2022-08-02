package src;

/**
 * {@code ProgramResult} is an interpretation result of BazaScript program.
 */

public class ProgramResult {

    private final String value;

    ProgramResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
