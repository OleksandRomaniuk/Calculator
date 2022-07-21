package src.baseProcedure;


import org.junit.jupiter.params.provider.Arguments;
import src.AbstractInterpreterEvaluationTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class ForLoopTest extends AbstractInterpreterEvaluationTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("for (i = 2; i > 0; i = i - 1;){print i;};", "[2.0][1.0]",
                        "For loop test test has failed"),

                of("for (i = 0; i < 6; i = i + 1;){print i;};", "[0.0][1.0][2.0][3.0][4.0][5.0]",
                        "For loop test test has failed")
        );

    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(
                of("for(i = 3 > 0; i > 0; i = i - 1;{print i;};)", 25,
                        "Closing bracket is on the wrong position"),

                of("for(i = 3 > 0; i > 0; i = i - 1;{print i;};", 25,
                        "For loop without closing bracket test has not throw exception"),

                of("for(i = 3 > 0; i > 0; i = i - 1;)", 17,
                        "For loop without body test has not throw exception"),

                of("for(i = 3 > 0;i > 0; i = i - 1;){print a;", 18,
                        "For loop without closing brace test has not throw exception"),

                of("for(i = 3 > 0; i> 0; i = i - 1;)print i;};", 13,
                        "For loop without opening brace test has not throw exception")
        );
    }
}
