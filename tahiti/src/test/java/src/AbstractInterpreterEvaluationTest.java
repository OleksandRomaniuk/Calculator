package src;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

public class AbstractInterpreterEvaluationTest {

    private final Tahiti tahiti = new Tahiti();

    @ParameterizedTest
    @MethodSource("positiveCases")
    void testPositiveCases(String mathScript, String expected, String assertMsg)
            throws IncorrectProgramException {

        InputProgram script = new InputProgram(mathScript);
        ProgramResult output = tahiti.interpret(script);

        Assertions.assertEquals(expected, output.getValue(), assertMsg);
    }
    @ParameterizedTest
    @MethodSource("negativeCases")
    void testNegativeCase(String mathScript, int expectedPosition, String assertMsg)  {

        InputProgram script = new InputProgram(mathScript);

        IncorrectProgramException programException =
                Assertions.assertThrows(IncorrectProgramException.class,
                        () -> tahiti.interpret(script));

        Assertions.assertEquals(expectedPosition, programException.getErrorPosition(), assertMsg);
    }
}

