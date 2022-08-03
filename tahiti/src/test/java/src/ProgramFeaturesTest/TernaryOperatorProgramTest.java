package src.ProgramFeaturesTest;

import src.AbstractProgramTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

public class TernaryOperatorProgramTest extends AbstractProgramTest {

    static Stream<Arguments> positiveCases() {
        return Stream.of(
               of("a = 3; b = a > 2 ? a > 1 ? 2 : 5; print(b);", "[2.0]",
                        "Chain of two ternary operators with true condition test has failed"),

                of("a = 3; b = a > 1 ? a > 2 ? a >=3 ? 2 : 5; print(b);", "[2.0]",
                        "Chain of three ternary operators with true condition test has failed"),

                of("a = 3; b = a > 9 ? 5 : a > 5 ? 2 : a>=3 ? 2 : 5; print(b);", "[2.0]",
                        "Chain of three ternary operators with false condition test has failed"));

    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 6; b = a > 2 ?? 3 : 4; print(b);", 18,
                        "Ternary operator with  test has not throw exception"),

                of("a = 6; b = a ? 3 : 4; print(b);", 13,
                        "Ternary operator with double type instead of relational expression test has not throw exception"),

                of("a = 6; b =  ? 3 : 4; print(b);", 12,
                        "Ternary operator with missing relational expression test has not throw exception")
        );
    }
}
