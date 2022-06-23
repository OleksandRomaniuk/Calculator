package program;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import src.calculator.ExpressionException;
import src.tahiti.ProgramInput;
import src.tahiti.ProgramResult;
import src.tahiti.Tahiti;

import static org.junit.jupiter.api.Assertions.assertEquals;

public abstract class AbstractProgramTest {

    private final Tahiti tahiti = new Tahiti();

    @ParameterizedTest
    @MethodSource("positiveCases")
    void positiveCase(String programSource, String programOutput, String errorMessage) throws ExpressionException {

        ProgramInput program = new ProgramInput(programSource);

        ProgramResult output = tahiti.interpret(program);

        assertEquals(programOutput, output.getValue(), errorMessage);
    }

    @ParameterizedTest
    @MethodSource("negativeCases")
    void negativeCase(String programSource, int errorPosition, String errorMessage) throws ExpressionException {

        ProgramInput program = new ProgramInput(programSource);

        ExpressionException exception =
                Assertions.assertThrows(ExpressionException.class, () -> tahiti.interpret(program));

        assertEquals(errorPosition, exception.getErrorPosition(), errorMessage);
    }
}
