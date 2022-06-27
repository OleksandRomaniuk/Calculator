package src;

/**
 * {@code ProgramResult} is a tiny type which is used to store interpretation of BazaScript program.
 */

public class ProgramResult {

    private String value;

    public ProgramResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
