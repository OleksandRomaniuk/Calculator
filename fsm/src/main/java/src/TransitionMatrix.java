package src;

import java.util.Set;

/**
 * {@code src.TransitionMatrix} is an interface that can be used to
 * build state-transition matrix for {@link FiniteStateMachine}.
 * @param <S> states for {@link FiniteStateMachine}.
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
