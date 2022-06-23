package src.tahiti.initvar;


import src.calculator.impl.fsm.util.ResolvingException;
import src.calculator.impl.math.MathElementResolver;
import src.fsm.Input;
import src.fsm.Transducer;
import src.tahiti.VariableHolder;

import java.util.Optional;

public class variableExpressionTransducer implements Transducer<VariableHolder> {

    private final MathElementResolver resolver;

    variableExpressionTransducer(MathElementResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean doTransition(Input inputChain, VariableHolder outputChain) throws ResolvingException {
        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){

            outputChain.putVariable(outputChain.getLastName(), resolve.get());

            return true;
        }
        return false;
    }
}
