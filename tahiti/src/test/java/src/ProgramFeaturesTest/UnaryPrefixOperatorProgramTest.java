package src.ProgramFeaturesTest;

import org.junit.jupiter.params.provider.Arguments;
import src.AbstractProgramTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

public class UnaryPrefixOperatorProgramTest extends AbstractProgramTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a=6; --a; print(--a) ;print(a);", "[4.0][4.0]",
                        "Unary prefix decrement operator test has failed."),

                of("a= 1; b = 3; c = ++a + ++a + (++a) - ++b + (++b); print(a); print(b);", "[4.0][5.0]",
                        "Unary prefix increment operator that used like a part of expression test has failed."),

                of("a=0; c = ++a; print(c);", "[1.0]",
                        "Unary prefix increment operator inside procedure test has failed."),

                of("a=0; c = ++a + a; print(c);", "[2.0]",
                        "Unary prefix increment operator that used like separate statement test has failed."),

                of("a=0; b=4; ++a; print(++a); print(a); print(--b);", "[2.0][2.0][3.0]",
                        "Unary prefix increment operator test has failed."),

                of(" a = 1; b = ++a; print(--a); print(b);", "[1.0][2.0]",
                        "Unary prefix decrement operator with expression test has failed."));
    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(
                of("++5;", 0, "Unary prefix operator with number test has not throw exception."),

                of("++a;", 3, "Unary prefix operator with uninitialised variable test has not throw exception."),

                of("+a;", 0, "Unary prefix operator with only one plus sign test has not throw exception."),

                of("++(5+2);", 0, "Unary prefix operator with expression test has not throw exception."),

                of("++;", 0, "Unary prefix operator without variable test has not throw exception."),

                of("a = 0; ++a++; print(a)", 10, "Not supported postfix operator with variable test has not throw exception."),

                of("a = 0; +++a; print(a)", 7, "Not supported unary prefix operator with variable test has not throw exception."),

                of("a = 0; ++min(4, 5); print(a)", 12, "Unary prefix operator with function test has not throw exception.")

        );
    }
}
