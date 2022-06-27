package src.initvar;



import src.fsm.Input;
import src.fsm.Transducer;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolver;

import java.util.Optional;

public class variableExpressionTransducer implements Transducer<InitVarContext> {

    private final MathElementResolver resolver;

    variableExpressionTransducer(MathElementResolver resolver) {
        this.resolver = resolver;
    }

    @Override
    public boolean doTransition(Input inputChain, InitVarContext outputChain) throws ResolvingException {
        Optional<Double> resolve = resolver.resolve(inputChain);
        if (resolve.isPresent()){

//            outputChain.putVariable(outputChain.getLastName(), resolve.get());

            outputChain.setVariableValue(resolve.get());

            return true;
        }
        return false;
    }
}
