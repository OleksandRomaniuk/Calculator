package src.util;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.runtime.WithContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramFactory;


import java.util.List;

/**
 * Implementation of {@link Transducer}
 * for parsing and executing statements inside braces.
 *
 */

public class CodeBlockTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final ProgramFactory factory;

    public CodeBlockTransducer(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        ExceptionThrower<ExecutionException> exceptionThrower = errorMessage -> {
            throw new ExecutionException(errorMessage);
        };

        FiniteStateMachine<Object, O, ExecutionException> machine = FiniteStateMachine.chainMachine(
                exceptionThrower,

                List.of(),

                List.of(Transducer.checkAndPassChar('{'),

                        new StatementListTransducer<O>(factory).named("Statement list inside braces"),

                        Transducer.checkAndPassChar('}')
                )
        );

        return machine.run(inputChain, outputChain);
    }
}
