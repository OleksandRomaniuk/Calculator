package src.fsm.function;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.CharSequenceReader;
import src.ExceptionThrower;
import src.Transducer;
import src.identifier.IdentifierMachine;

import java.util.function.BiConsumer;

/**
 * {@code FunctionNameTransducer} is an implementation of {@link Transducer}
 * that produce a name of function for {@link FunctionHolder} output.
 */

public class FunctionNameTransducer<O, E extends Exception> implements Transducer<O, E> {

    private final BiConsumer<O, String> resultConsumer;

    private final ExceptionThrower<E> exceptionThrower;

    private static final Logger logger = LoggerFactory.getLogger(FunctionNameTransducer.class);

    public FunctionNameTransducer(BiConsumer<O, String> resultConsumer, ExceptionThrower<E> exceptionThrower) {

        this.resultConsumer = Preconditions.checkNotNull(resultConsumer);

        this.exceptionThrower = exceptionThrower;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws E {

        StringBuilder stringBuilder = new StringBuilder();

        IdentifierMachine<E> identifierMachine = IdentifierMachine.create(exceptionThrower, Character::isLetter);

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
