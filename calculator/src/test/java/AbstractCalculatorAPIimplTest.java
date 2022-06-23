import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import src.CalculatorAPI;
import src.ExpressionException;
import src.impl.CalculatorAPIimpl;
import src.impl.MathematicalExpression;
import src.impl.Result;
import src.impl.fsm.util.ResolvingException;

import static org.junit.jupiter.api.Assertions.assertEquals;


public abstract class AbstractCalculatorAPIimplTest {

    private final CalculatorAPI calculator = new CalculatorAPIimpl();

    @ParameterizedTest
    @MethodSource("positiveCases")
    void positiveCase(String mathExpression, double resultExpected, String errorMessage) throws ExpressionException, ResolvingException {

        Result result = calculator.calculate(new MathematicalExpression(mathExpression));

        assertEquals(resultExpected, result.getResult(), errorMessage);
    }

    @ParameterizedTest
    @MethodSource("negativeCases")
    void negativeCase(String mathExpression, int expectedErrorPosition, String errorMessage) {

        MathematicalExpression expression = new MathematicalExpression(mathExpression);

        ExpressionException exception =
                Assertions.assertThrows(ExpressionException.class, () -> calculator.calculate(expression));

        assertEquals(expectedErrorPosition, exception.getErrorPosition(), errorMessage);
    }
}
