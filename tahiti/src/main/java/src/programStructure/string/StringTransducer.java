package src.programStructure.string;


import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.Transducer;
import src.runtime.ScriptContext;
import src.util.ExecutionException;

import java.util.List;

/**
 * Implementation of {@link Transducer} which create  string machine that parse string input chain
 */

class StringTransducer implements Transducer<ScriptContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        FiniteStateMachine<Object, ScriptContext, ExecutionException> stringMachine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(),
                List.of(Transducer.checkAndPassChar('\''), new StringInnerTransducer().named("String inner"), Transducer.checkAndPassChar('\''))
        );

        return stringMachine.run(inputChain, outputChain);
    }
}
