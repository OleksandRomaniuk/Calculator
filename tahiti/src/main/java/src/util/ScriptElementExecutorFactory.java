package src.util;

/**
 * {@code ScriptElementExecutorFactory} is a functional interface that
 * represents a factory pattern for creating {@link ScriptElementExecutor}.
 */

public interface ScriptElementExecutorFactory {

    ScriptElementExecutor create(ScriptElement element);
}
