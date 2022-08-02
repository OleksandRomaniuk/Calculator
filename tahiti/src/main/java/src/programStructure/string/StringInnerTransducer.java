package src.programStructure.string;


import src.CharSequenceReader;
import src.Transducer;
import src.identifier.IdentifierMachine;
import src.runtime.ScriptContext;
import src.type.StringValue;
import src.util.ExecutionException;

/**
 * Implementation of {@link Transducer} that produce string value.
 */

class StringInnerTransducer implements Transducer<ScriptContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        StringBuilder stringValue = new StringBuilder();

        IdentifierMachine<ExecutionException> stringReadMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        }, character -> character != '\'');

        if (stringReadMachine.run(inputChain, stringValue)) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            outputChain.systemStack().current().pushOperand(new StringValue(stringValue.toString()));

            return true;
        }

        return false;
    }
}
