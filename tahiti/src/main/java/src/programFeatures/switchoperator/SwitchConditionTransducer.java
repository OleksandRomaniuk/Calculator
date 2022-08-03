package src.programFeatures.switchoperator;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.Transducer;
import src.tahiti.*;

import java.util.List;

/**
 * Implementation of {@link Transducer}
 * Class is used for read variable inside brackets as compared value for switch operator.
 */

class SwitchConditionTransducer implements Transducer<SwitchOperatorContext, ExecutionException> {

    private final ProgramFactory factory;

    SwitchConditionTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchOperatorContext outputChain) throws ExecutionException {

        outputChain.setInitialParsingStatus(outputChain.isParseOnly());

        FiniteStateMachine<Object, SwitchOperatorContext, ExecutionException> machine = FiniteStateMachine.chainMachine(
                errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(),
                List.of(Transducer.checkAndPassChar('('),
                        new ComparedValueTransducer(factory).named("Variable inside switch operator"),
                        Transducer.checkAndPassChar(')'))
        );

        return machine.run(inputChain, outputChain);
    }
}
