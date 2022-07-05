package src.baseProcedure;

import org.junit.jupiter.params.provider.Arguments;
import src.AbstractInterpreterEvaluationTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class InterpreterEvaluationTest extends AbstractInterpreterEvaluationTest {
    static Stream<Arguments> positiveCases(){
        return Stream.of(

                of("a = 5; print(a); ", "[5.0]", "Evaluation of initialization and print failed"),
                of("a = 5; b = 4; print(a + b); ", "[9.0]", "Evaluation of initialization two variables and sum of them failed"),
                of("a = 5; b = 3 + 7; print(a * b); ", "[50.0]", "Evaluation of initialization two variables and multiply of them failed"),
                of("a = 5; b = 0 + 7; c = 7 - 2; print(5 * ( b + 3) + c); ", "[55.0]",
                        "Evaluation of initialization 3 variables and multiply/sum of them failed"),
                of("a = 5; b = 3 + 7; c = 7 - 4; d = min(a, b, c); print(d); ", "[3.0]",
                        "Evaluation of function min with variable arguments failed")

        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(

                of("print(a);", 7, "Not initialized variable test has not throw exception"),
                of("a = a; print(a);", 5, "Wrong initialization of variable test has not throw exception"),
                of("a = 6; print(a;", 14, "Not closed brackets inside procedure test has not throw exception"),
                of("a = 6; printa);", 7, "Not opened brackets inside procedure test has not throw exception")

        );
    }
}
