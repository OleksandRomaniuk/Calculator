package src.programFeatures.variable;


import src.CharSequenceReader;
import src.identifier.IdentifierMachine;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElementExecutor;

/**
 * Implementation of {@link ProgramElementExecutor}
 * that used to read variables
 */

public class ReadVariableExecutor implements ProgramElementExecutor {

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        StringBuilder variableName = new StringBuilder();

        IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create( errorMessage -> {
            throw new ExecutionException(errorMessage);
        }, Character::isLetter);

        if (nameMachine.run(inputChain, variableName)) {

            String variableIdentifier = variableName.toString();

            if (output.hasVariable(variableIdentifier)) {

                if (!output.isParseOnly()) {

                    output.systemStack().current().pushOperand(output.memory().getVariableValueFromCache(variableIdentifier));
                }

                return true;

            }
            else {

                throw new ExecutionException("Not initialized variable.");
            }
        }

        return false;
    }
}
