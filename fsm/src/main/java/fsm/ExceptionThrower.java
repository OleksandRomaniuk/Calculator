package fsm;

@FunctionalInterface
public interface ExceptionThrower<E extends Exception> {

    void throwException(String errorMessage) throws E;
}
