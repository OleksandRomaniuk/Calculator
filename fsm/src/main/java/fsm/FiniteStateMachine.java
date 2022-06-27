package fsm;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * {@code FiniteStateMachine} is a realization of concept of
 * <a href = "https://en.wikipedia.org/wiki/Finite-state_machine">Finite state machine</a>
 * <p>
 * It can change states with help of {@link Transducer}
 * </p>
 *
 * <p>
 * Requires a {@link TransitionMatrix} which is a definition of directed graph
 * </p>
 *
 * @param <S> States for {@link TransitionMatrix}
 * @param <O> Output chain
 */

public class FiniteStateMachine<S, O> {

    @SafeVarargs
    public static <O> FiniteStateMachine<Object, O> oneOfMachine(Transducer<O>... transducers) {

        Map<Object, Transducer<O>> registers = new LinkedHashMap<>();

        Object startState = new Object();
        Object finishState = new Object();

        MatrixBuilder<Object> builder = new MatrixBuilder<>();

        builder.withStartState(startState).withFinishState(finishState);

        for (Transducer<O> transducer : transducers) {

            Object transducerState = new Object();

            builder.allowTransition(startState, transducerState)
                    .allowTransition(transducerState, finishState);

            registers.put(transducerState, transducer);
        }

        FiniteStateMachine<Object, O> machine = new FiniteStateMachine<>(builder.build());

        for (Map.Entry<Object, Transducer<O>> entry : registers.entrySet()) {

            machine.registerTransducer(entry.getKey(), entry.getValue());
        }

        machine.registerTransducer(startState, Transducer.illegalTransition());

        machine.registerTransducer(finishState, Transducer.autoTransition());

        return machine;
    }

    private static final Logger logger = LoggerFactory.getLogger(FiniteStateMachine.class);

    private TransitionMatrix<S> matrix;

    private final Map<S, Transducer<O>> transducers = new HashMap<>();

    private boolean allowedSkippingWhitespaces;

    public FiniteStateMachine(TransitionMatrix<S> matrix, boolean allowedSkippingWhitespaces) {

        this.matrix = Preconditions.checkNotNull(matrix);
        this.allowedSkippingWhitespaces = allowedSkippingWhitespaces;
    }

    public FiniteStateMachine() {
    }

    protected FiniteStateMachine(TransitionMatrix<S> matrix) {

        this(matrix, false);
    }

    public boolean run(CharSequenceReader inputChain, O outputChain) throws ResolvingException {

        inputChain.savePosition();

        if (logger.isInfoEnabled()) {

            logger.info("Start of machine: {} ", getClass().getSimpleName());
        }

        S currentState = matrix.getStartState();

        while (!matrix.getFinishState().equals(currentState)) {

            Optional<S> nextState = makeNextStep(inputChain, outputChain, currentState);

            if (nextState.isEmpty()) {

                if (matrix.getStartState().equals(currentState)) {

                    return false;
                }

                if (matrix.isTemporaryState(currentState)) {

                    if (logger.isInfoEnabled()) {

                        logger.info("Rejected temporary state -> {}", currentState);
                    }

                    inputChain.restorePosition();

                    return false;
                }

                throw new ResolvingException("Deadlock at state: " + currentState);
            }

            currentState = nextState.get();
        }

        return true;
    }

    private Optional<S> makeNextStep(CharSequenceReader inputChain, O outputChain, S currentState) throws ResolvingException {
        if (allowedSkippingWhitespaces) {

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

    protected void registerTransducer(S state, Transducer<O> transducer) {

        transducers.put(state, transducer);
    }
}
