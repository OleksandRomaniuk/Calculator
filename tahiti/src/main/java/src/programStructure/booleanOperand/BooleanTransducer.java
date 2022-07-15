package src.programStructure.booleanOperand;

import src.CharSequenceReader;
import src.Transducer;
import src.identifier.IdentifierMachine;
import src.runtime.ScriptContext;
import src.type.BooleanValueVisitor;
import src.type.Value;
import src.util.ExecutionException;

public class BooleanTransducer implements Transducer<ScriptContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {
        StringBuilder variableName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        if (nameMachine.run(inputChain, variableName)) {

            if (outputChain.hasVariable(variableName.toString())) {

                Value variable = outputChain.memory().getVariable(variableName.toString());

                if (!BooleanValueVisitor.isBoolean(variable)) {

                    return false;
                }

                if (outputChain.isParseOnly()) {

                    return true;
                }
                outputChain.systemStack().current().pushOperand(variable);
                return true;
            }
        }
        return false;
    }
}
