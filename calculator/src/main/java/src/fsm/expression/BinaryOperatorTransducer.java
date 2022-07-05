package src.fsm.expression;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.Transducer;
import src.AbstractBinaryOperator;
import src.BinaryOperatorFactory;


import java.util.function.BiConsumer;

/**
 * BinaryOperatorTransducer is an implementation of  Transducer
 * that produce an AbstractBinaryOperator to ShuntingYard output
 */

public class BinaryOperatorTransducer<O, E extends Exception> implements Transducer<O, E> {

    private final BinaryOperatorFactory factory;

    private final BiConsumer<O, AbstractBinaryOperator> operatorConsumer;

    public BinaryOperatorTransducer(BinaryOperatorFactory factory, BiConsumer<O, AbstractBinaryOperator> operatorConsumer) {
        this.factory = factory;
        this.operatorConsumer = operatorConsumer;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) {

        Preconditions.checkNotNull(inputChain, outputChain);

        if (!inputChain.canRead()) {
            return false;
        }

        var operator = factory.create(inputChain.readOperator());

        if (operator.isPresent()) {

            operatorConsumer.accept(outputChain, operator.get());

            inputChain.incrementPosition();

            return true;
        }
        if(inputChain.previous() == '>' || inputChain.previous() == '<'){
            inputChain.decrementPosition();
        }
        return false;
    }
}
