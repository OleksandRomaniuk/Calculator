package src.util;


public interface ProgramFactory {

    ProgramElementExecutor create(ProgramElement element);
}
