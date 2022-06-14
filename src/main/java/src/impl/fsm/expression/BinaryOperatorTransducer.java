package src.impl.fsm.expression;

import com.google.common.base.Preconditions;
import src.impl.fsm.Transducer;
import src.impl.fsm.util.BinaryOperatorFactory;
import src.impl.fsm.util.Input;
import src.impl.fsm.util.PrioritizedOperator;
import src.impl.fsm.util.ShuntingYardStack;

import java.util.Optional;

class BinaryOperatorTransducer implements Transducer<ShuntingYardStack> {

    private final BinaryOperatorFactory factory = new BinaryOperatorFactory();

    @Override
    public boolean doTransition(Input inputChain, ShuntingYardStack outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        if (!inputChain.canRead()) {
            return false;
        }

        Optional<PrioritizedOperator> operator = factory.create(inputChain.read());

        if (operator.isPresent()) {
            outputChain.pushOperator(operator.get());

            inputChain.incrementPosition();

            return true;
        }
        return false;
    }
}
