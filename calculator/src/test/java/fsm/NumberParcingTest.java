package fsm;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class NumberParcingTest extends AbstractCalculatorAPIimplTest {

    static Stream<Arguments> positiveCases(){
        return Stream.of(
                of("1", 1.0, "Evaluation of single digit failed."),
                of("12345", 12345.0, "Evaluation of integer failed."),
                of("-563", -563.0, "Evaluation of negative integer failed."),
                of("1253.23", 1253.23, "Evaluation of double failed."),
                of("-9876.24", -9876.24, "Evaluation of negative double failed.")
        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(
                of("--1441", 1, "Evaluation of number with doubled minus did not failed."),
                of("12.34.5", 5, "Evaluation of number with doubled dot did not failed."),
                of("12.", 3, "Evaluation of number without numbers after dot did not failed."),
                of("--1", 1, "Extra negative sign has not failed the test"),
                of("-1.2.3", 4, "Second dot has not failed the test")
        );
    }
}
