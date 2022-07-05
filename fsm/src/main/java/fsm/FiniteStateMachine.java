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

        var startState = new Object(){
            @Override
            public String toString() {
                return "START";
            }
        };
        var finishState = new Object(){
            @Override
            public String toString() {
                return "FINISH";
            }
        };

        var builder = new MatrixBuilder<Object>();

        builder.withStartState(startState).withFinishState(finishState);

        for (var transducer : transducers) {

            Object transducerState = new Object(){
                @Override
                public String toString() {
                    return transducer.toString();
                }
            };

            builder.allowTransition(startState, transducerState)
                    .allowTransition(transducerState, finishState);

            registers.put(transducerState, transducer);
        }

        var machine = new FiniteStateMachine<Object, O, E>(builder.build(), exceptionThrower);

        for (var entry : registers.entrySet()) {

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


        var startState = new Object(){
            @Override
            public String toString() {
                return "START";
            }
        };
        var finishState = new Object(){
            @Override
            public String toString() {
                return "FINISH";
            }
        };

        var builder = new MatrixBuilder<Object>();

        builder.withStartState(startState).withFinishState(finishState);


        int i = 0;
        long count = transducers.size();
        var temp = new Object();
        for (var transducer: transducers){

            var transducerState = new Object(){
                @Override
                public String toString() {
                    return transducer.toString();
                }
            };

            if (i == 0){
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

        var objectOFiniteStateMachine = new FiniteStateMachine<Object, O, E>(builder.build(), exceptionThrower);

        for (var entry : registers.entrySet()){
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

    protected FiniteStateMachine(TransitionMatrix<S> matrix, ExceptionThrower<E> eExceptionThrower) {

        this(matrix, eExceptionThrower, false);
    }

    public boolean run(CharSequenceReader inputChain, O outputChain) throws E {

        var startPosition = inputChain.position();

        if (logger.isInfoEnabled()) {

            logger.info("Start of machine: {} ", getClass().getSimpleName());
        }

        var currentState = matrix.getStartState();

        while (!matrix.getFinishState().equals(currentState)) {

            var nextState = makeNextStep(inputChain, outputChain, currentState);

            if (nextState.isEmpty()) {

                if (matrix.getStartState().equals(currentState)) {

                    return false;
                }

                if (matrix.isTemporaryState(currentState)) {

                    if (logger.isInfoEnabled()) {

                        logger.info("Rejected temporary state -> {}", currentState);
                    }

                    inputChain.setPosition(startPosition);

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
        var possibleTransitions = matrix.getPossibleTransitions(currentState);

        for (var potentialState : possibleTransitions) {

            var transducer = transducers.get(potentialState);

            if (transducer.doTransition(inputChain, outputChain)) {

                if (logger.isInfoEnabled()) {

                    logger.info("Machine on work: {} -> {} on index {}", getClass().getSimpleName(), potentialState, inputChain.position());
                }

                return Optional.of(potentialState);
            }
        }
        return Optional.empty();
    }

    protected void registerTransducer(S state, Transducer<O, E> transducer) {

        transducers.put(state, transducer);
    }
}
