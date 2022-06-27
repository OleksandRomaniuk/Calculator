package fsm;

import java.util.Set;



public interface TransitionMatrix<S> {

    S getStartState();

    S getFinishState();

    Set<S> getPossibleTransitions(S state);

    static <S> MatrixBuilder<S> builder() {
        return new MatrixBuilder<>();
    }

    boolean isTemporaryState(S currentState);
}
