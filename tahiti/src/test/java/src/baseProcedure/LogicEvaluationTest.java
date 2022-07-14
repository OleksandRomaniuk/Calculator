package src.baseProcedure;

import org.junit.jupiter.params.provider.Arguments;
import src.AbstractInterpreterEvaluationTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class LogicEvaluationTest extends AbstractInterpreterEvaluationTest {
    static Stream<Arguments> positiveCases(){
        return Stream.of(

                of("a = 1; b = 20; c = 10; d = a+5 < 10 && b > 100 || c <= 10; println(d);", "[true]", "Boolean expression inside procedure test has failed"),
                of("a = 1; b = 20; c = 10; d = a+5 < 10 && b > 100; println(d);", "[true]", "Boolean expression inside procedure test has failed"),
                of("a = 1; b = 20; c = 10; d = a+5 < 10 || b > 100 || c <= 10; println(d);", "[true]", "Boolean expression inside procedure test has failed")
        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(


        );
    }
}
