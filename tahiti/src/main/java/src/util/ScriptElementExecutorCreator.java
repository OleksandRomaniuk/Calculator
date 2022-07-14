package src.util;

/**
 * {@code ScriptElementExecutorCreator} is a functional interface that can be used to
 * create {@link ScriptElementExecutor}.
 */

@FunctionalInterface
public interface ScriptElementExecutorCreator {

    ScriptElementExecutor create();
}
