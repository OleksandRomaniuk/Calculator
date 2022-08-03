package src.ProgramFeaturesTest;

import org.junit.jupiter.params.provider.Arguments;
import src.AbstractProgramTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class StringTypeProgramTest extends AbstractProgramTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a = 'Hello World!'; print(a);", "[Hello World!]",
                        "Simple string type test has failed"),

                of("a = 'Hello'; b =a + ' World!'; print(b);",
                        "[Hello World!]",
                        "String concatenation with first string and second number test has failed"),

                of("year = 'Year has '; days = 365;print(year + ' ' + days);",
                        "[Year has  365.0]",
                        "String concatenation test has failed"),

                of("a = 0; b = a < 2 ? 'One' : 'Two'; print(b);", "[One]",
                        "String type inside ternary operator test has failed")

        );
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 'aaa; print(a);", 19,
                        "String type initialisation without closing quote test has not throw exception"),

                of("a = aaa; print(a);", 7,
                        "String type initialisation without  quote"),

                of("a = 'aaa' + 'a; print(a);", 25,
                        "String type initialisation without  quote"),

                of("a = '5' + 1'; print(a);", 11,
                        "String type initialisation without  quote"),

                of("a = 25 + '  '; print(a);", 9,
                        "Not allowed concatenation with first operand as double test has not throw exception"),

                of("b = 1; a = b + '  '; print(a);", 15,
                        "Not allowed concatenation with first operand as variable of double type test has not throw exception"),

                of("a = ''; print(a);", 5,
                        "String type initialisation with null value test has not throw exception"),

                of("a = aaa'; print(a);", 7,
                        "String type initialisation without opening quote test has not throw exception"),

                of("a = 'aaa' + ; print(a);", 12,
                        "String concatenation without second operand test has not throw exception"),

                of("a = 'aaa' - 'bbb'; print(a);", 10,
                        "String concatenation with not supported operator '-' test has not throw exception"),

                of("a = 'aaa' / 'bbb'; print(a);", 10,
                        "String concatenation with not supported operator '/' test has not throw exception"),

                of("a = 'aaa' * 'bbb'; print(a);", 10,
                        "String concatenation with not supported operator '*' test has not throw exception")


        );
    }
}
