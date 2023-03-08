package src.programFeatures.variable;

import com.google.common.base.Preconditions;
import src.*;
import src.calucator.fsm.expression.OperatorReader;
import src.operators.PrefixUnaryOperatorFactory;
import src.operators.UnaryOperatorFactory;
import src.util.ProduceVariableContext;
import src.type.Value;
import src.tahiti.*;

import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Implementation of {@link Transducer}
 * that used to create and produce unary operator to {@link ProduceVariableContext}.
 */

public class UnaryPrefixOperatorTransducer implements Transducer<ProduceVariableContext, ExecutionException> {

    private final UnaryOperatorFactory factory = new PrefixUnaryOperatorFactory();

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProduceVariableContext outputChain) throws ExecutionException {
        Preconditions.checkNotNull(inputChain, outputChain);

        if (!inputChain.canRead()) {
            return false;
        }

        Optional<String> operatorSign = OperatorReader.read(inputChain, factory.getOperators());

        if (operatorSign.isPresent()) {

            Optional<UnaryOperator<Value>> unaryOperator = factory.create(operatorSign.get());

            unaryOperator.ifPresent(outputChain::setUnaryOperator);

        }

        return true;

    }
}
