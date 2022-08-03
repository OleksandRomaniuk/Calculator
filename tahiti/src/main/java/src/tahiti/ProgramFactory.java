package src.tahiti;

/**
 * Functional interface for realization a factory pattern for {@link ProgramElementExecutor}.
 */


public interface ProgramFactory {

    ProgramElementExecutor create(ProgramElement element);
}
