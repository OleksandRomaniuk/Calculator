package src.calculator.impl.fsm.expression;

import com.google.common.base.Preconditions;
import src.fsm.Transducer;
import src.calculator.impl.fsm.util.BinaryOperatorFactory;
import src.fsm.Input;
import src.calculator.impl.fsm.util.PrioritizedOperator;
import src.calculator.impl.fsm.util.ShuntingYardStack;

import java.util.Optional;

/**
 *Implementation of {@link Transducer} , which create {@link PrioritizedOperator}
 *
 *
 */
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
