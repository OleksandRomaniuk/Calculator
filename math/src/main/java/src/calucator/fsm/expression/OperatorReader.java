package src.calucator.fsm.expression;

import src.CharSequenceReader;

import java.util.*;

/**
 * Class that used for read operator signs from  CharSequenceReader
 */

public class OperatorReader {

    private OperatorReader() {
    }

    public static Optional<String> read(CharSequenceReader inputChain, Set<String> operators) {

        int startPosition = inputChain.position();

        List<String> operatorList = new ArrayList<>(operators);

        operatorList.sort(Comparator.comparingInt(String::length));

        Collections.reverse(operatorList);

        for (String operatorSign : operatorList) {

            int operatorLength = operatorSign.length();

            Optional<String> optionalOperator = inputChain.copyOfRange(operatorLength);

            if (optionalOperator.isPresent()) {

                if (operatorSign.equals(optionalOperator.get().intern())) {

                    return optionalOperator;
                } else {
                    inputChain.setPosition(startPosition);
                }
            }
        }

        return Optional.empty();
    }
}
