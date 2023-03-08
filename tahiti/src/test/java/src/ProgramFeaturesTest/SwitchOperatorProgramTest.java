package src.ProgramFeaturesTest;

import src.AbstractProgramTest;
import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class SwitchOperatorProgramTest extends AbstractProgramTest {
    static Stream<Arguments> positiveCases() {
        return Stream.of(
                of("a = 3 > 2; switch (a) { case 3>4 : {b = 1;} case 3==3 : {b = 2;} default: {b = 3;}}; print(b);", "[2.0]",
                        "Switch operator with relational expression as an option"),

                of("a = 5;switch (a){case 5:{b = 1; c = 8;} case 4: {b = 2;} default: {b = 3;}}; print(b); print(c);", "[1.0][8.0]",
                        "Switch operator with first true case test has failed."),

                of("a = 4; switch (a) { case 5: {b = 1;} case 3: {b = 1} case 4: {b = 2} default: {b = 3;}}; print(b);", "[2.0]",
                        "Switch operator with second true case test has failed."),

                of("a = 3; switch (a) { case 5: {b = 1;} case 4: {b = 2} default: {b = 4;}}; print(b);", "[4.0]",
                        "Switch operator with third true case test has failed."),

                of("a = 3; switch (a) { case 1+2: {b = 1;} case 2+2: {b = 2;} default: {b = 3;}}; print(b);", "[1.0]",
                        "Switch operator with simple expression as an option."),

                of("a = 3; switch (a) { case min(3,5): {b = 1;} case 2+2: {b = 2;} default: {b = 3;}}; print(b);", "[1.0]",
                        "Switch operator with function as an option"),

                of("a = 3; c = 3; switch (a) { case c: {b = 1;} case 2+2: {b = 2;} default: {b = 3;}}; print(b);", "[1.0]",
                        "Switch operator with variable as an option")
        );

    }

    static Stream<Arguments> negativeCases() {
        return Stream.of(

                of("a = 5; switch  { case 4: {b = 1;} case 4: {b = 2;} default: {b = 3}};", 15,
                        "Switch operator without condition test has not throw exception"),

                of("a = 5; switch (a) { case : {b = 1;} case 4: {b = 2;} default: {b = 3;}};", 25,
                        "Switch operator without condition in first case test has not throw exception"),

                of("a = 5; switch (a) { case 5 : {b = 1;} case : {b = 2;} default: {b = 3;}};", 43,
                        "Switch operator without condition in second case test has not throw exception"),

                of("a = 5; switch (a) { case 5  {b = 1;} case : {b = 2;} default: {b = 3;}};", 28,
                        "Switch operator without colon test has not throw exception"),

                of("a = 5; switch (a) { default };", 20,
                        "Switch operator without any case test has not throw exception"),

                of("a = 5; switch a { case 4 : {b = 1;} case 3 : {b = 2} default: { b = 3;} };", 14,
                        "Switch operator without brackets test has not throw exception"),

                of("a = 5; switch (a)  case 4 : {b = 1;} case 3 : {b = 2} default: { b = 3;} };", 19,
                        "Switch operator without opening brace test has not throw exception"),

                of("a = 5; switch (a) { case 4 : {b = 1;} case 3 : {b = 2;} default: { b = 3;} ;", 75,
                        "Switch operator without closing brace test has not throw exception")

        );
    }
}
