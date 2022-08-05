package src.programFeatures.varopertor;

import com.google.common.base.Preconditions;

import src.CharSequenceReader;
import src.runtime.ProgramContext;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElementExecutor;
import src.tahiti.ProgramFactory;

/**
 * Implementation of {@link ProgramElementExecutor} that create and run {@link InitVarMachine}.
 */

public class InitVarExecutor implements ProgramElementExecutor {

    private final ProgramFactory factory;

    public InitVarExecutor(ProgramFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        InitVarContext initVarContext = new InitVarContext(output);

        InitVarMachine initVarMachine = InitVarMachine.create(factory, errorMessage -> {
            throw new ExecutionException(errorMessage);
        });

        if (initVarMachine.run(inputChain, initVarContext)) {

            if (!output.isParseOnly()) {

                initVarContext.getScriptContext().memory()
                        .setVariable(initVarContext.getVariableName(), initVarContext.getVariableValue());

            }

            return true;
        }

        return false;
    }
}
