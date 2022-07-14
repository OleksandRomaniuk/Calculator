package src.booleanOperator;

import fsm.CharSequenceReader;
import fsm.Transducer;
import fsm.identifier.IdentifierMachine;
import fsm.type.BooleanValueVisitor;
import fsm.type.Value;
import src.runtime.ScriptContext;
import src.util.ExecutionException;

/**
 * BinaryOperatorTransducer is an implementation of  Transducer
 * that produce an AbstractBinaryOperator to ShuntingYard output
 */

public class BooleanOperatorTransducer implements Transducer<ScriptContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {
        var variableName = new StringBuilder();

        var nameMachine = IdentifierMachine.create(errorMessage -> {
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
