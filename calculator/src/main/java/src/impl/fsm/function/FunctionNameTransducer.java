package src.impl.fsm.function;


import fsm.Input;
import fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.impl.fsm.function.name.IdentifierMachine;
import src.impl.fsm.util.FunctionHolder;
import src.impl.fsm.util.ResolvingException;


/**
 *
 * Implementation of {@link Transducer} for {@link StringBuilder} output.
 *
 */

class FunctionNameTransducer implements Transducer<FunctionHolder> {
    private static final Logger logger = LoggerFactory.getLogger(FunctionNameTransducer.class);

    @Override
    public boolean doTransition(Input inputChain, FunctionHolder outputChain) throws ResolvingException {

        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){
            if (logger.isInfoEnabled()){

                logger.info("Function name - {}", stringBuilder);
            }
            outputChain.setFunctionName(stringBuilder.toString());
            return true;
        }
        return false;
    }

}
