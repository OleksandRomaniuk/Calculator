package src.calucator.fsm.expression;

import com.google.common.base.Preconditions;
import src.calucator.fsm.expression.OperatorReader;
import src.operators.AbstractBinaryOperator;
import src.operators.BinaryOperatorFactory;
import src.CharSequenceReader;
import src.Transducer;

import java.util.Optional;
import java.util.function.BiConsumer;


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

        Optional<String> operatorSigns = OperatorReader.read(inputChain, factory.getOperators());

        if (operatorSigns.isPresent()) {

            Optional<AbstractBinaryOperator> operator = factory.create(operatorSigns.get());

            if (operator.isPresent()) {

                operatorConsumer.accept(outputChain, operator.get());

                return true;
            }

            return false;

        }

        return false;
    }
}
