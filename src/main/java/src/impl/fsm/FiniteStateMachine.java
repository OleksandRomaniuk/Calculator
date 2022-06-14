package src.impl.fsm;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.impl.fsm.util.Input;
import src.impl.fsm.util.ResolvingException;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class FiniteStateMachine<S, O> {

    private static final Logger logger = LoggerFactory.getLogger(FiniteStateMachine.class);

    private final TransitionMatrix<S> matrix;

    private final Map<S, Transducer<O>> transducers = new HashMap<>();

    private boolean allowedSkippingWhitespaces;

    public FiniteStateMachine(TransitionMatrix<S> matrix, boolean allowedSkippingWhitespaces) {

        this.matrix = Preconditions.checkNotNull(matrix);
        this.allowedSkippingWhitespaces = allowedSkippingWhitespaces;
    }

    protected FiniteStateMachine(TransitionMatrix<S> matrix) {

        this(matrix, false);
    }

    public boolean run(Input inputChain, O outputChain) throws ResolvingException {

        if (logger.isInfoEnabled()) {

            logger.info("Start of machine: {} for {}", getClass().getSimpleName(), inputChain.toString());
        }

        S currentState = matrix.getStartState();

        while (!matrix.getFinishState().equals(currentState)) {

            Optional<S> nextState = makeNextStep(inputChain, outputChain, currentState);

            if (nextState.isEmpty()) {

                if (matrix.getStartState().equals(currentState)) {

                    return false;
                }

                throw new ResolvingException("");
            }

            currentState = nextState.get();
        }

        return true;
    }

    private Optional<S> makeNextStep(Input inputChain, O outputChain, S currentState) throws ResolvingException {
        if (allowedSkippingWhitespaces){

            inputChain.skipWhitespaces();
        }
        Set<S> possibleTransitions = matrix.getPossibleTransitions(currentState);

        for (S potentialState : possibleTransitions) {

            Transducer<O> transducer = transducers.get(potentialState);

            if (transducer.doTransition(inputChain, outputChain)) {

                if (logger.isInfoEnabled()) {

                    logger.info("Machine on work: {} -> {} on index {}", getClass().getSimpleName(), potentialState, inputChain.position());
                }

                return Optional.of(potentialState);
            }
        }
        return Optional.empty();
    }

    protected void registerTransducer(S state, Transducer<O> transducer){

        transducers.put(state, transducer);
    }
}
