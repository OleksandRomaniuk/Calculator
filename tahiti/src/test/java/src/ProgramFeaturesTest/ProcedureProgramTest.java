package src.ProgramFeaturesTest;

import org.junit.jupiter.params.provider.Arguments;
import src.AbstractProgramTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

public class ProcedureProgramTest extends AbstractProgramTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(

                of("print(777);", "[777.0]", "Print procedure with double type test has failed"),

                of("print(5<=2);", "[false]", "Print procedure with boolean type test has failed"),

                of("a = 5<2; b = 3<4; print(a&&b);", "[false]", "Print procedure with boolean expression test has failed"),

                of("print('Hello');", "[Hello]", "Print procedure with string type test has failed"),

                of("print('Hello ' + 'there');", "[Hello there]", "Print procedure with string expression test has failed"),

                of("a = 7; print(a<=2);", "[false]", "Boolean expression inside procedure test has failed")
        );
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(
                of("print(a);", 7, "Not initialized variable test has not throw exception"),

                of("a = 6; print(a;", 14, "Not closed brackets inside procedure test has not throw exception"),

                of("a = 6; printa);", 13, "Not opened brackets inside procedure test has not throw exception")



        );
    }

}
