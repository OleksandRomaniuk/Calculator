package src.logicaloperand;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.CharSequenceReader;
import src.Transducer;
import src.identifier.IdentifierMachine;
import src.runtime.ScriptContext;
import src.type.BooleanValueVisitor;
import src.type.Value;
import src.util.ExecutionException;

/**
 * {@code ReadBooleanVariableTransducer} is an implementation of {@link Transducer}
 * that produce a boolean variable to {@link ScriptContext} output
 * for {@link LogicalOperandMachine}.
 */

class ReadBooleanVariableTransducer implements Transducer<ScriptContext, ExecutionException> {

    private static final Logger logger = LoggerFactory.getLogger(ReadBooleanVariableTransducer.class);

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {
        StringBuilder variableName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        int position = inputChain.position();

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
