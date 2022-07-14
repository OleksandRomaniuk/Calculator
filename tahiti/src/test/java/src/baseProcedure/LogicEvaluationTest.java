package src.baseProcedure;

import org.junit.jupiter.params.provider.Arguments;
import src.AbstractInterpreterEvaluationTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class LogicEvaluationTest extends AbstractInterpreterEvaluationTest {
    static Stream<Arguments> positiveCases(){
        return Stream.of(

                of("a = 1 < 10; b = 20 > 100; d = a && b; print(d);", "[false]", "Simple conjunction test has failed."),

                of("a = 1 < 10; b = 20 > 100; c = 1 < 5; d = a && b || c; print(d);", "[true]", "Simple conjunction test has failed."),

                of("a = 1; b = 20; c = 10; d = b > 100 || c > 9; print(d);", "[true]", "Simple disjunction test has failed."),

                of("a = 1; b = 20; c = 10; print(b > 100 || c > 9);", "[true]", "Logical expression inside procedure test has failed."),
                of("a = 7; print(a<=2);", "[false]", "Boolean expression inside procedure test has failed"),
                of("a = 5 > 2; print(a);", "[true]", "Boolean variable initialization test has failed"),
                of("print(5);", "[5.0]", "Evaluation of print failed")
        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(

                of("a = 7; print(a=<2);", 14, "Illegal operation"),
                of("a = 5 >< 2; print a);", 6, "Illegal operation"),
                of("a = 1; b = 20; d = a + 5 && a - 10;", 25,
                        "Logical operand between numeric expressions test has not throw exception"),

                of("a = 1; b = 20; d = a > 5 & & a < 10;", 25,
                        "Space inside logical operand test has not throw exception")

        );
    }
}
