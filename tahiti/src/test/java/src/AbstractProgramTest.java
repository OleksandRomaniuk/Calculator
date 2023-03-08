package src;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractProgramTest {

    private final Tahiti tahiti = new Tahiti();

    @ParameterizedTest
    @MethodSource("positiveCases")
    void testPositiveCases(String programCode, String expected, String assertMsg) throws IncorrectProgramException {
        InputProgram script = new InputProgram(programCode);
        ProgramResult output = tahiti.interpret(script);

        Assertions.assertEquals(expected, output.getValue(), assertMsg);
    }

    @ParameterizedTest
    @MethodSource("negativeCases")
    void negativeCase(String programCode, int expectedErrorPosition, String errorMessage) {

        InputProgram script = new InputProgram(programCode);

        IncorrectProgramException programException =
                Assertions.assertThrows(IncorrectProgramException.class,
                        () -> tahiti.interpret(script));

        assertEquals(expectedErrorPosition, programException.getErrorPosition(), errorMessage);
    }
}
