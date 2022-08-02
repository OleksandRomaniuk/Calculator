package src.baseProcedure;

import org.junit.jupiter.params.provider.Arguments;
import src.AbstractInterpreterEvaluationTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class StringTest extends AbstractInterpreterEvaluationTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a = 'Thunder '; b = 'Love and ' + ' ' + a; print(b);",
                        "[Love and Thunder]",
                        "String concatenation with number test has failed"),

                of("a = 'Hello World!'; print(a);", "[Hello World!]",
                        "Simple string type test has failed"),

                of("a = 0; b = a < 2 ? 'One' : 'Two'; print(b);", "[One]",
                        "String type inside ternary operator test has failed")

        );
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 'aaa; print(a);", 19,
                        "String type initialisation without closing quote test has not throw exception"),

                of("a = aaa'; print(a);", 7,
                        "String type initialisation without opening quote test has not throw exception"),

                of("a = 'aaa' + ; print(a);", 4,
                        "String concatenation without second operand test has not throw exception"),

                of("a = 'aaa' - 'bbb'; print(a);", 10,
                        "String concatenation with not supported operator '-' test has not throw exception"),

                of("a = 'aaa' / 'bbb'; print(a);", 10,
                        "String concatenation with not supported operator '/' test has not throw exception"),

                of("a = 'aaa' * 'bbb'; print(a);", 10,
                        "String concatenation with not supported operator '*' test has not throw exception"),

                of("a = ''; print(a);", 5,
                        "String type initialisation with null value test has not throw exception")

        );
    }
}
