package com.java.calculator.fsm.expression.fsm;

import com.java.calculator.fsm.expression.AbstractCalculatorTest;
import org.junit.jupiter.params.provider.Arguments;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.of;

class BracketsMachineTest extends AbstractCalculatorTest {

    static Stream<Arguments> positiveCases(){
        return Stream.of(
                of("(3+5)*2", 16.0, "Evaluation of brackets operator failed.."),
                of("(3+5)*(2+1)", 24.0, "Evaluation of brackets operator failed.."),
                of("2 * (2+3)", 10.0, "Simple one brackets test has failed"),
                of("2*(7+3/(2+3))", 15.2, "Double nesting brackets test has failed"),
                of("2*(7+3/(2+3)^4)", 14.0096, "Double nesting brackets with degree test has failed")
        );
    }

    static Stream<Arguments> negativeCases(){
        return Stream.of(
                of("1+3*(4+2", 8, "Opened but not closed brackets test has failed"),
                of("1+3*4)+2", 5, "Closed but not opened brackets test has failed"),
                of("()", 1, "Empty brackets test has failed"),
                of("((1-2)+3", 8, "Not closing brackets did not failed"),
                of("(1-2", 4,  "Not opening brackets did not failed."),
                of("()", 1,  "Not expression in brackets did not failed.")
        );
    }
}
