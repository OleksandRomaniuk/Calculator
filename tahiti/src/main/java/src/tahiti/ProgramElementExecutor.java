package src.tahiti;

import src.CharSequenceReader;
import src.runtime.ProgramContext;


/**
 * Functional interface for analysis and interpretation of the input Tahiti code
 */

@FunctionalInterface
public interface ProgramElementExecutor {

    boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException;
}
