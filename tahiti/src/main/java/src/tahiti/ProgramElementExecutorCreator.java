package src.tahiti;

/**
 * Functional interface for creating {@link ProgramElementExecutor}.
 */
@FunctionalInterface
public interface ProgramElementExecutorCreator {

    ProgramElementExecutor create();
}
