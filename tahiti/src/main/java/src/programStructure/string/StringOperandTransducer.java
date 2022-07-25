package src.programStructure.string;


import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.Transducer;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ExecutorProgramElementTransducer;
import src.util.ProgramElement;
import src.util.ProgramFactory;

/**
 * Implementation of {@link Transducer} that create and run
 * one of operand machine for possible operands in string expression.
 */

public class StringOperandTransducer implements Transducer<ScriptContext, ExecutionException> {

    private final ProgramFactory factory;

    public StringOperandTransducer(ProgramFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ScriptContext outputChain) throws ExecutionException {

        FiniteStateMachine<Object, ScriptContext, ExecutionException> machine = FiniteStateMachine.oneOfMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                new StringTransducer(),
                new ExecutorProgramElementTransducer(ProgramElement.NUMBER, factory).named("Number in string expression"),
                new ExecutorProgramElementTransducer(ProgramElement.BRACKETS, factory).named("Brackets in string expression"),
                new ExecutorProgramElementTransducer(ProgramElement.READ_VARIABLE, factory).named("Variable in string expression")
        );

        return machine.run(inputChain, outputChain);
    }
}
