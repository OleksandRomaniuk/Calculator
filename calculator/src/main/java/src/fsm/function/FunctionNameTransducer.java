package src.fsm.function;

import fsm.CharSequenceReader;
import fsm.ResolvingException;
import fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.IdentifierMachine;

import java.util.function.BiConsumer;


public class FunctionNameTransducer<O> implements Transducer<O> {

    private final BiConsumer<O, String> resultConsumer;

    private static final Logger logger = LoggerFactory.getLogger(FunctionNameTransducer.class);

    public FunctionNameTransducer(BiConsumer<O, String> resultConsumer) {

        this.resultConsumer = resultConsumer;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ResolvingException {

        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine identifierMachine = IdentifierMachine.create();

        if (identifierMachine.run(inputChain, stringBuilder)){

            if (logger.isInfoEnabled()){

                logger.info("Function name - {}", stringBuilder);
            }

            resultConsumer.accept(outputChain, stringBuilder.toString());
            return true;
        }

        return false;
    }

}
