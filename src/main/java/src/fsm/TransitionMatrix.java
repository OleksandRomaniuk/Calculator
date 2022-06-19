package src.fsm;

import com.google.common.base.Preconditions;

import java.util.*;

public interface TransitionMatrix<S> {

    S getStartState();

    S getFinishState();

    Set<S> getPossibleTransitions(S state);

    static <S> MatrixBuilder<S> builder() {
        return new MatrixBuilder<>();
    }

    class MatrixBuilder<S> {

        private S startState;
        private S finishState;

        private final Map<S, Set<S>> transitions = new TreeMap<>();

        public MatrixBuilder<S> withStartState(S startState) {

            Preconditions.checkState(this.startState == null, "Start state is already defined");

            this.startState = Preconditions.checkNotNull(startState);

            return this;
        }

        public MatrixBuilder<S> withFinishState(S finishState) {

            Preconditions.checkState(this.finishState == null, "Finish state is already defined");

            this.finishState = Preconditions.checkNotNull(finishState);

            return this;
        }

        @SafeVarargs
        public final MatrixBuilder<S> allowTransition(S currentState, S... states) {

            transitions.put(currentState, new TreeSet<>(List.of(states)));

            return this;
        }

        public TransitionMatrix<S> build() {
            return new TransitionMatrix<>() {
                @Override
                public S getStartState() {
                    return startState;
                }

                @Override
                public S getFinishState() {
                    return finishState;
                }

                @Override
                public Set<S> getPossibleTransitions(S state) {
                    return transitions.get(state);
                }
            };
        }
    }
}
