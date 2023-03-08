package src.programFeatures.string;

import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.Transducer;
import src.runtime.ProgramContext;
import src.util.ProgramElementTransducer;
import src.tahiti.*;


/**
 * Implementation of {@link Transducer} for possible operands in string expression.
 */

public class StringExpressionTransducer implements Transducer<ProgramContext, ExecutionException> {

    private final ProgramFactory factory;

    public StringExpressionTransducer(ProgramFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ProgramContext outputChain) throws ExecutionException {


        FiniteStateMachine<Object, ProgramContext, ExecutionException> machine = FiniteStateMachine.oneOfMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },

                new StringStructureTransducer(),

                new ProgramElementTransducer(ProgramElement.NUMBER, factory).named("Number in string expression"),

                new ProgramElementTransducer(ProgramElement.BRACKETS, factory).named("Brackets in string expression"),

                new ProgramElementTransducer(ProgramElement.READ_VARIABLE, factory).named("Variable in string expression")
        );

        return machine.run(inputChain, outputChain);
    }
}
