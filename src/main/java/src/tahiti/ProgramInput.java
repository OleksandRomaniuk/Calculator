package src.tahiti;

/**
 * {@code BazaScriptProgram} is a tiny type which is used to store code for BazaScript program.
 */

public class ProgramInput {

    private final String value;

    public ProgramInput(String value) {
        this.value = value;
    }

    String getValue() {
        return value;
    }
}
