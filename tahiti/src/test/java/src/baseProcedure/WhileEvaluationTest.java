package src.baseProcedure;

import org.junit.jupiter.params.provider.Arguments;
import src.AbstractInterpreterEvaluationTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class WhileEvaluationTest extends AbstractInterpreterEvaluationTest {
    static Stream<Arguments> positiveCases(){
        return Stream.of(
                
                of("a = 0; while(a<4){a = a+1; print(a);};", "[1.0][2.0][3.0][4.0]",
                        "While loop test has failed"),
                of("a = 2; while(a<4){a = a+1;}; print(a);", "[4.0]",
                        "While loop test with code after loop has failed")

        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(
                of("a; while(a<4){a = a+1; print(a);};", 0, "Not initialized variable test has not throw exception"),
                of("a=0; while(a<4{a = a+1; print(a);};", 14, "Not opened  inside procedure test has not throw exception ")

        );
    }
}
