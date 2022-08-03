package src.programFeatures.string;

import src.CharSequenceReader;
import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.runtime.ProgramContext;
import src.tahiti.*;


import java.util.List;

/**
 * Implementation of {@link Transducer} that create and run {@link FiniteStateMachine}
 * for parsing string structure
 */

class StringStructureTransducer implements Transducer<ProgramContext, ExecutionException> {

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramContext outputChain) throws ExecutionException {

        FiniteStateMachine<Object, ProgramContext, ExecutionException> stringMachine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },

                List.of(),

                List.of(Transducer.checkAndPassChar('\''),
                        new StringValueTransducer().named("String value"),
                        Transducer.checkAndPassChar('\'')
                )
        );
        return stringMachine.run(inputChain, outputChain);
    }
}
