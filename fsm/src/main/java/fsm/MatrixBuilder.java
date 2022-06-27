package fsm;

import com.google.common.base.Preconditions;
import com.google.common.collect.Multimap;
import com.google.common.collect.MultimapBuilder;

import java.util.*;

public class MatrixBuilder<S> {

    private S startState;
    private S finishState;
    private final Collection<S> temporaryStates = new HashSet<>();

    private final Multimap<S, S> transitions = MultimapBuilder.hashKeys().arrayListValues().build();

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

        for (S state: states){

            transitions.put(currentState, state);
        }

        return this;
    }

    @SafeVarargs
    public final MatrixBuilder<S> withTemporaryState(S... states){

        temporaryStates.addAll(Arrays.asList(states));

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
                return new LinkedHashSet<>(transitions.get(state) );
            }

            @Override
            public boolean isTemporaryState(S currentState) {
                return temporaryStates.contains(currentState);
            }
        };
    }
}
