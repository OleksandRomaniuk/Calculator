package src.programStructure.switchoperator;

import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.Transducer;
import src.util.ExecutionException;
import src.util.ProgramFactory;

import java.util.List;


class SwitchVariableTransducer implements Transducer<SwitchContext, ExecutionException> {

    private final ProgramFactory factory;

    SwitchVariableTransducer(ProgramFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchContext outputChain) throws ExecutionException {

        FiniteStateMachine<Object, SwitchContext, ExecutionException> machine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(),
                List.of(Transducer.checkAndPassChar('('),
                        new ComparedValueTransducer(factory).named("Variable in sitch"),
                        Transducer.checkAndPassChar(')'))
        );

        return machine.run(inputChain, outputChain);
    }
}
