package src.baseProcedure;


import org.junit.jupiter.params.provider.Arguments;
import src.AbstractInterpreterEvaluationTest;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class SwitchOperatorTest extends AbstractInterpreterEvaluationTest {
    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a = 4; switch (a) { case 1+3: {b = 1;} case 2+1: {b = 2;} default: {b = 3;}}; print(b);", "[1.0]",
                        "Switch operator doesn't understand expression."),

                of("a = 3; switch (a) { case min(3,5): {b = 1;} case 2+2: {b = 2;} default: {b = 3;}}; print(b);", "[1.0]",
                        "Switch operator doesn't understand function")

        );

    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 5; switch (a)  case 4 : {b = 1;} case 3 : {b = 2} default: { b = 3;} };", 19,
                        "Switch operator without opening brace test has not throw exception"),

                of("a = 5; switch (a) { case 4 : {b = 1;} case 3 : {b = 2;} default: { b = 3;} ;", 75,
                        "Switch operator without closing brace test has not throw exception")

        );
    }
}
