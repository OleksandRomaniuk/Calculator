package calculator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import src.calculator.impl.fsm.util.BinaryOperatorFactory;
import src.calculator.impl.fsm.util.PrioritizedOperator;
import src.calculator.impl.fsm.util.ShuntingYard;

import java.util.Optional;


class ShuntingYardTest {
    ShuntingYard shuntingYard = new ShuntingYard();;
    Optional<PrioritizedOperator> operator;
    private final BinaryOperatorFactory factory = new BinaryOperatorFactory();

    @ParameterizedTest
    @CsvSource({"1, +, 3, 4, 1+3 failed",
            "4, -, 2, 2, 4-2 failed",
            "6, /, 3, 2, 6/3 failed",
            "2, *, 3, 6, 2*3 failed"
    })

    void testPushOperand(int first, char operand, int second, int expected, String msg){
        shuntingYard.pushOperand(first);
        operator = factory.create(operand);
        shuntingYard.pushOperator(operator.get());
        shuntingYard.pushOperand(second);
        double result = shuntingYard.peekResult();
        Assertions.assertEquals(expected, result, msg);
    }
}