package src.programFeatures.booleanexpression;

import src.CharSequenceReader;
import src.Transducer;
import src.identifier.IdentifierMachine;
import src.runtime.ProgramContext;
import src.type.BooleanValueVisitor;
import src.type.Value;
import src.tahiti.*;

/**
 * Implementation of {@link Transducer}
 * that used to read  boolean variables which is operands of boolean expression.
 */

class BooleanVariableTransducer implements Transducer<ProgramContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramContext outputChain) throws ExecutionException {
        StringBuilder variableName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        }, Character::isLetter);

        if (nameMachine.run(inputChain, variableName)) {

            if (outputChain.hasVariable(variableName.toString())) {

                Value variable = outputChain.memory().getVariableValueFromCache(variableName.toString());

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
