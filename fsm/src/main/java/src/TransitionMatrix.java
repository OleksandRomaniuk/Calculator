package src;

import java.util.Set;

/**
 * Interface that can be used to build state-transition matrix
 */

public interface TransitionMatrix<S> {

    S getStartState();

    S getFinishState();

    Set<S> getPossibleTransitions(S state);

    static <S> MatrixBuilder<S> builder() {
        return new MatrixBuilder<>();
    }

    boolean isTemporaryState(S currentState);
}
