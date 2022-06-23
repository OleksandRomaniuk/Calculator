
package program;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.provider.Arguments;
import src.tahiti.Tahiti;


import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.params.provider.Arguments.of;

public class CompilerTest extends AbstractProgramTest {


        static Stream<Arguments> positiveCases () {
            return Stream.of(
                    of("a=2; println(a);", "2", "Simple interpreter  test has failed"),
                    of("a=2; b=a+2; println(b);", "4", "Simple interpreter  test has failed"),
                    of("a=2; b=a+2+a; println(b);", "6", "Simple interpreter  test has failed")
            );
        }

        static Stream<Arguments> negativeCases () {
            return Stream.of(

            );
        }
    }


