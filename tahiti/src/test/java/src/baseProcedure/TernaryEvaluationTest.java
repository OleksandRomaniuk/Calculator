package src.baseProcedure;


import org.junit.jupiter.params.provider.Arguments;
import src.AbstractInterpreterEvaluationTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class TernaryEvaluationTest extends AbstractInterpreterEvaluationTest {
    static Stream<Arguments> positiveCases() {
        return Stream.of(


                of("a = 0; b = a < 2 ? 3 : 8; print(b);", "[3.0]",
                        "Simple ternary operator test has failed"),
                of("a = 1; b = a > 2 ? 5 : a > 0 ? 5+2*(3+5) : 0; print(b);", "[21.0]",
                        "Chain of two ternary operators test has failed"),

                of("a = 1; b = a > 2 ? 5 : a > 0 ? min(2,5) : 0; print(b);", "[2.0]",
                        "Chain of two ternary operators with function inside expression test has failed"),
                of("a = 0; print(a < 2 ? 3 : 8);", "[3.0]",
                        "Ternary operator inside procedure test has failed"),

                of("a = 0; b = a < 2 ? 3<2 : 2<8; print(b);", "[false]",
                        "Simple ternary operator test with boolean value initialization has failed")


        );
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 6; b = a < 2  3 : 8; print(b);", 18,
                        "Ternary operator without symbol ? test has not throw exception"),

                of("a = 6; b = a < 2 ? 3 : ; print(b);", 17,
                        "Ternary operator without expression if condition false test has not throw exception"),

                of("a = 6; b = a < 2 : 4; print(b);", 17,
                        "Ternary operator without expression if condition true test has not throw exception")

        );
    }
}

