package src.fsm;


import org.junit.jupiter.params.provider.Arguments;
import src.AbstractCalculatorAPIimplTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class ExpressionCalculationTest extends AbstractCalculatorAPIimplTest {

    static Stream<Arguments> positiveCases(){
        return Stream.of(
                of("1 + 2", 3.0, "Simple sum action test has failed"),
                of("2*4", 8.0, "Simple multiplication action test has failed"),
                of("1+2*3", 7.0, "Several digits test has failed"),
                of("3*4   +6*7+9^2", 135.0, "Negative several digits test has failed"),
                of("2 * 4", 8.0, "Space between operands test has failed")
        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(

                of("1**2", 2, "Double minus action test has failed"),
                of("1-+2", 2, "Several operators test has failed"),
                of("2//2", 2, "Double division action test has failed"),
                of("2.22.2/5", 4, "Error in number test has failed")
        );
    }
}


