package src.ProgramFeaturesTest;

import src.AbstractProgramTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class ForLoopProgramTest extends AbstractProgramTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("for(i = 0; i < 3; i = i + 1){print(i);};", "[0.0][1.0][2.0]",
                        "Simple for loop test test has failed"),

                of("for(i = 0; i < 5; i = i+1){print(i);};", "[0.0][1.0][2.0][3.0][4.0]",
                        "Simple for loop test test has failed"),

                of("for(i = min(1,2,3); i < 3; i = i+1){print(i);};", "[1.0][2.0]",
                        "Simple for loop test test has failed"),

                of("for(i = 3; i > 0; i = i - 1){ for(j = 3; j > 0; j = j - 1){print(j);}; print(i);};",
                        "[3.0][2.0][1.0][3.0][3.0][2.0][1.0][2.0][3.0][2.0][1.0][1.0]",
                        "Nested for loop test test has failed"),

                of("for(i = 3; i > 0; i = i - 1){j = 4; k= 3;}; print(i, j, k);", "[0.0, 4.0, 3.0]",
                        "For loop with code after it test has failed"),

                of("j = 4; for(i = 3; i > 0; i = i - 1){k = j < 2 ? 3 : 8;}; print(k);", "[8.0]",
                        "For loop with ternary operator test has failed"),

                of("for(i = 0; i < 10; i = i + 5){print(i);};", "[0.0][5.0]",
                        "Simple for loop test test has failed"),

                of("a =2;for(i = 10; i > 0; i = i-a){print(i);};", "[10.0][8.0][6.0][4.0][2.0]",
                        "Simple for loop test test has failed")
        );
    }
    static Stream<Arguments> negativeCases() {
        return Stream.of(
                of("for(i = 3; i > 0; i = i - 1)print a;};", 28,
                        "For loop without opening brace test has not throw exception"),

                of("for(i = 3 i > 0; i = i - 1)print a;};", 10,
                        "For loop without first separator"),

                of("for(i = 3; i > 0 i = i - 1)print a;};", 17,
                        "For loop without second separator"),

                of("for(i = 3; i ! 0; i = i - 1)print a;};", 10,
                        "For loop with unexpected symbol"),

                of("for i = 3; i > 0; i = i - 1;){print(i);};", 3,
                        "For loop without opening bracket test has not throw exception"),

                of("for(i = 3 i > 0; i = i - 1){print(i);};", 10,
                        "For loop condition without separator test has not throw exception"),

                of("for(i = 3; i > 0; i =i - 1{print(i);};", 26,
                        "For loop without closing bracket test has not throw exception"),

                of("for(i = 3; i > 0; i = i - 1)", 28,
                        "For loop without body test has not throw exception"),

                of("for(i = 3; i > 0; i = i - 1){print (a);", 37,
                        "For loop without closing brace test has not throw exception"),

                of("for(i = 3; i > 0; i = i - 1)print a;};", 28,
                        "For loop without")
        );
    }
}
