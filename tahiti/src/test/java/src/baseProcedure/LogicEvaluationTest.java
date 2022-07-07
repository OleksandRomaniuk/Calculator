package src.baseProcedure;

import org.junit.jupiter.params.provider.Arguments;
import src.AbstractInterpreterEvaluationTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class LogicEvaluationTest extends AbstractInterpreterEvaluationTest {
    static Stream<Arguments> positiveCases(){
        return Stream.of(

                of("a = 1; b = 20; c = 10; d = a < 10 && b > 100 || c <= 10; println(d);", "[true]", "Boolean expression inside procedure test has failed"),
                of("a = 7; print(a<=2);", "[false]", "Boolean expression inside procedure test has failed"),
                of("a = 5 > 2; print(a);", "[true]", "Boolean variable initialization test has failed"),
                of("print(5);", "[5.0]", "Evaluation of print failed")
        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(

                of("a = 7; print(a=<2);", 14, "Illegal operation"),
                of("a = 5 >< 2; print a);",7, "Illegal operation")

        );
    }
}
