package src.programFeatures.variable;

import src.CharSequenceReader;
import src.Transducer;
import src.identifier.IdentifierMachine;
import src.util.ProduceVariableContext;
import src.type.DoubleValueVisitor;
import src.tahiti.*;

/**
 * Implementation of {@link Transducer}
 * that can be used to read variable inside expression.
 */

public class ProduceVariableTransducer implements Transducer<ProduceVariableContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProduceVariableContext outputChain) throws ExecutionException {

        StringBuilder variableName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
            throw new ExecutionException(errorMessage);
        }, Character::isLetter);

        if (nameMachine.run(inputChain, variableName)) {

            String variableIdentifier = variableName.toString();

            if (outputChain.getScriptContext().hasVariable(variableIdentifier)) {

                if (outputChain.isParseOnly()) {
                    return true;
                }

                var variableValue = outputChain.getScriptContext().memory().getVariableValueFromCache(variableIdentifier);

                if (!DoubleValueVisitor.isDouble(variableValue)) {
                    return false;
                }

                if (outputChain.hasUnaryOperator()) {

                    variableValue = outputChain.applyOperator(variableValue);

                    outputChain.getScriptContext().memory().setVariableToCache(variableIdentifier, variableValue);
                }


                outputChain.getScriptContext().systemStack().current().pushOperand(variableValue);

                return true;

            }
            else {

                throw new ExecutionException("Operation with not initialised variable " + variableName + " cannot be performed");
            }
        }

        return false;
    }
}
