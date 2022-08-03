package src.programFeatures.string;

import src.CharSequenceReader;
import src.Transducer;
import src.identifier.IdentifierMachine;
import src.runtime.ProgramContext;
import src.type.StringValue;
import src.tahiti.*;


/**
 * Implementation of {@link Transducer} that produce string value to {@link ProgramContext}.
 */

class StringValueTransducer implements Transducer<ProgramContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramContext outputChain) throws ExecutionException {

        StringBuilder stringValue = new StringBuilder();

        IdentifierMachine<ExecutionException> stringReadMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        }, character -> character != '\'');

        if (stringReadMachine.run(inputChain, stringValue)) {

            if (!outputChain.isParseOnly()) {

                outputChain.systemStack().current().pushOperand(new StringValue(stringValue.toString()));
            }

            return true;
        }

        return false;
    }
}
