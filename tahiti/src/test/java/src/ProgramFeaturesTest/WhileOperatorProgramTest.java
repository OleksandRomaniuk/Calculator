package src.ProgramFeaturesTest;

import src.AbstractProgramTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class WhileOperatorProgramTest extends AbstractProgramTest {
    static Stream<Arguments> positiveCases() {
        return Stream.of(

                of("a = 0; while(a<4){a = a+1; print(a);};", "[1.0][2.0][3.0][4.0]",
                        "While loop test has failed"),

                of("a = 2; while(a<4){a = a+1;}; a = 7; b = 7 < 2; print(a); print(b);", "[7.0][false]",
                        "While loop test with code after loop test has failed"),

                of("a = 2; b = 7; while(a<4){a = a+1; while(b<10){b = b + 1;}}; print(a); print(b);", "[4.0][10.0]",
                        "Nested while loop test with code after loop has failed")
        );
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 0; while(a<4{a = a+1; print(a);};", 16, "While loop without closing brackets in condition" +
                        "test has not throw exception"),

                of("a = 0; whilea<4){a = a+1; print(a);};", 12, "While loop without opening brackets in condition " +
                        "test has not throw exception"),

                of("a = 0; while(a<4)a = a+1; print(a);};", 17, "While loop without opening brace" +
                        "test has not throw exception"),

                of("a = 0; while(a<4){a = a+1; print(a);;", 36, "While loop without closing brace" +
                        "test has not throw exception")


        );
    }
}
