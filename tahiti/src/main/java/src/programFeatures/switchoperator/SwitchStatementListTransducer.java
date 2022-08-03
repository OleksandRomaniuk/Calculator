package src.programFeatures.switchoperator;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.util.StatementListTransducer;
import src.tahiti.*;


import java.util.List;

/**
 * Implementation of {@link Transducer}
 * that create and run machine for parsing statement list inside braces
 */

class SwitchStatementListTransducer implements Transducer<SwitchOperatorContext, ExecutionException> {

    private final ProgramFactory factory;

    SwitchStatementListTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, SwitchOperatorContext outputChain) throws ExecutionException {

        ExceptionThrower<ExecutionException> exceptionThrower = errorMessage -> {
            throw new ExecutionException(errorMessage);
        };

        FiniteStateMachine<Object, SwitchOperatorContext, ExecutionException> machine = FiniteStateMachine.chainMachine(
                exceptionThrower,

                List.of(),

                List.of(Transducer.checkAndPassChar('{'),

                        new StatementListTransducer<SwitchOperatorContext>(factory).named("Statement list inside case"),

                        Transducer.<SwitchOperatorContext, ExecutionException>checkAndPassChar('}')
                                .and((input, output) -> {

                                    output.setParsePermission(output.isCaseExecuted());

                                    return true;
                                })
                )
        );

        return machine.run(inputChain, outputChain);
    }
}
