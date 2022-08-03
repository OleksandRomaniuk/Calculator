package src.programFeatures.variable;

import src.CharSequenceReader;
import src.ExceptionThrower;
import src.FiniteStateMachine;
import src.Transducer;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.util.ProduceVariableContext;
import src.tahiti.ProgramElementExecutor;

import java.util.List;

/**
 * Implementation of {@link ProgramElementExecutor} that used to create and run produceVariableMachine
 * which is intended to evaluate expression that contains unary prefix operator and double variable
 * or just read double variables in case of unary operator is missing.
 */

public class ProduceVariableExecutor implements ProgramElementExecutor {
    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        UnaryPrefixOperatorTransducer unaryPrefixOperatorTransducer = new UnaryPrefixOperatorTransducer();

        Transducer<ProduceVariableContext, ExecutionException> produceVariableTransducer = new ProduceVariableTransducer();

        ExceptionThrower<ExecutionException> exceptionThrower = errorMessage -> {
            throw new ExecutionException(errorMessage);
        };

        FiniteStateMachine<Object, ProduceVariableContext, ExecutionException> produceVariableMachine =
                FiniteStateMachine.chainMachine(
                        exceptionThrower,

                        List.of(unaryPrefixOperatorTransducer),

                        List.of(unaryPrefixOperatorTransducer, produceVariableTransducer)
                );

        ProduceVariableContext unaryOperatorContext = new ProduceVariableContext(output);

        return produceVariableMachine.run(inputChain, unaryOperatorContext);
    }
}
