package src;

import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;


public class FiniteStateMachine<S, O, E extends Exception> {


    private static final Logger logger = LoggerFactory.getLogger(FiniteStateMachine.class);

    private final TransitionMatrix<S> matrix;

    private final Map<S, Transducer<O, E>> transducers = new HashMap<>();

    private final ExceptionThrower<E> exceptionThrower;

    private final boolean allowedSkippingWhitespaces;

    @SafeVarargs
    public static <O, E extends Exception> FiniteStateMachine<Object, O, E> oneOfMachine(ExceptionThrower<E> exceptionThrower,
                                                                                         Transducer<O, E>... transducers) {

        Map<Object, Transducer<O, E>> registers = new LinkedHashMap<>();

        Object startState = new Object() {
            @Override
            public String toString() {
                return "START";
            }
        };
        Object finishState = new Object() {
            @Override
            public String toString() {
                return "FINISH";
            }
        };

        MatrixBuilder<Object> builder = new MatrixBuilder<>();

        builder.withStartState(startState).withFinishState(finishState);

        for (Transducer<O, E> transducer : transducers) {

            Object transducerState = new Object() {
                @Override
                public String toString() {
                    return transducer.toString();
                }
            };

            builder.allowTransition(startState, transducerState)
                    .allowTransition(transducerState, finishState);

            registers.put(transducerState, transducer);
        }

        FiniteStateMachine<Object, O, E> machine = new FiniteStateMachine<>(builder.build(), exceptionThrower);

        for (Map.Entry<Object, Transducer<O, E>> entry : registers.entrySet()) {

            machine.registerTransducer(entry.getKey(), entry.getValue());
        }

        machine.registerTransducer(startState, Transducer.illegalTransition());

        machine.registerTransducer(finishState, Transducer.autoTransition());

        return machine;
    }

    public static <O, E extends Exception>
    FiniteStateMachine<Object, O, E> chainMachine(ExceptionThrower<E> exceptionThrower,
                                                  List<Transducer<O, E>> temporaryTransducers,
                                                  List<Transducer<O, E>> transducers){

        Map <Object, Transducer<O, E>> registers =  new LinkedHashMap<>() ;


        Object startState = new Object() {
            @Override
            public String toString() {
                return "START";
            }
        };
        Object finishState = new Object() {
            @Override
            public String toString() {
                return "FINISH";
            }
        };

        MatrixBuilder<Object> builder = new MatrixBuilder<>();

        builder.withStartState(startState).withFinishState(finishState);


        int i = 0;
        long count = transducers.size();
        Object temp = new Object();
        for (Transducer<O, E> transducer : transducers) {

            Object transducerState = new Object() {
                @Override
                public String toString() {
                    return transducer.toString();
                }
            };

            if (i == 0) {
                builder.allowTransition(startState, transducerState);

            }
            else {
                builder.allowTransition(temp, transducerState);

            }
            temp = transducerState;
            i++;
            if( i == count ){
                builder.allowTransition(transducerState, finishState);
            }

            if (temporaryTransducers.contains(transducer)){
                builder.withTemporaryState(transducerState);
            }
            registers.put(transducerState, transducer);
        }

        FiniteStateMachine<Object, O, E> objectOFiniteStateMachine = new FiniteStateMachine<>(builder.build(), exceptionThrower);

        for (Map.Entry<Object, Transducer<O, E>> entry : registers.entrySet()) {
            objectOFiniteStateMachine.registerTransducer(entry.getKey(), entry.getValue());
        }
        objectOFiniteStateMachine.registerTransducer(startState, Transducer.illegalTransition());
        objectOFiniteStateMachine.registerTransducer(finishState, Transducer.autoTransition());

        return objectOFiniteStateMachine;
    }


    public FiniteStateMachine(TransitionMatrix<S> matrix, ExceptionThrower<E> exceptionThrower, boolean allowedSkippingWhitespaces) {

        this.matrix = Preconditions.checkNotNull(matrix);
        this.exceptionThrower = exceptionThrower;
        this.allowedSkippingWhitespaces = allowedSkippingWhitespaces;
    }

//    public src.FiniteStateMachine() {
//    }

    protected FiniteStateMachine(TransitionMatrix<S> matrix, ExceptionThrower<E> eExceptionThrower) {

        this(matrix, eExceptionThrower, false);
    }

    public boolean run(CharSequenceReader inputChain, O outputChain) throws E {

//        int startPosition = inputChain.position();

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

                    if (logger.isInfoEnabled()) {

                        logger.info("Restore position to : {} on index {}", currentState, inputChain.position());
                    }

                    return false;
                }

                exceptionThrower.throwException("Deadlock at state: " + currentState);
            }

            currentState = nextState.get();
        }

        return true;
    }

    private Optional<S> makeNextStep(CharSequenceReader inputChain, O outputChain, S currentState) throws E {
        if (allowedSkippingWhitespaces) {

            inputChain.skipWhitespaces();
        }
        Set<S> possibleTransitions = matrix.getPossibleTransitions(currentState);

        for (S potentialState : possibleTransitions) {

            Transducer<O, E> transducer = transducers.get(potentialState);

            if (matrix.isTemporaryState(potentialState)) {
                inputChain.savePosition();

                if (logger.isInfoEnabled()) {

                    logger.info("Save position on : {} on index {}", potentialState, inputChain.position());
                }
            }

            if (transducer.doTransition(inputChain, outputChain)) {

                if (logger.isInfoEnabled()) {

                    logger.info("Machine on work: {} -> {} on index {}", getClass().getSimpleName(), potentialState, inputChain.position());
                }

                return Optional.of(potentialState);
            }

            if (matrix.isTemporaryState(potentialState)) {

                inputChain.restorePosition();

                if (logger.isInfoEnabled()) {

                    logger.info("RESTORE POSITION : {} on index {}", potentialState, inputChain.position());
                }
            }
        }
        return Optional.empty();
    }

    protected void registerTransducer(S state, Transducer<O, E> transducer) {

        transducers.put(state, transducer);
    }
}
