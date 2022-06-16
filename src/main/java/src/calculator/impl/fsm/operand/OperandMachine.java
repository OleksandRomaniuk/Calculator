package src.calculator.impl.fsm.operand;

import src.calculator.impl.fsm.FiniteStateMachine;
import src.calculator.impl.fsm.Transducer;
import src.calculator.impl.fsm.TransitionMatrix;
import src.calculator.impl.ShuntingYardTransducer;
import src.calculator.impl.fsm.function.FunctionTransducer;
import src.calculator.impl.fsm.util.ShuntingYardStack;
import src.calculator.impl.math.MathElement;
import src.calculator.impl.math.MathElementResolverFactory;

import static src.calculator.impl.fsm.operand.OperandStates.*;
/**
 * NumberStateMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state when reading operand
 *
 */

public final class OperandMachine extends FiniteStateMachine<OperandStates, ShuntingYardStack> {

    public static OperandMachine create(MathElementResolverFactory factory) {

        TransitionMatrix<OperandStates> matrix = TransitionMatrix.<OperandStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .allowTransition(START, NUMBER, BRACKETS, FUNCTION)
                .allowTransition(NUMBER, FINISH)
                .allowTransition(FUNCTION, FINISH)
                .allowTransition(BRACKETS, FINISH)
                .build();

        return new OperandMachine(matrix, factory);
    }

    private OperandMachine(TransitionMatrix<OperandStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(NUMBER, new NumberTransducer(factory.create(MathElement.NUMBER)));
        registerTransducer(BRACKETS, new ShuntingYardTransducer(factory.create(MathElement.BRACKETS)));
        registerTransducer(FUNCTION, new FunctionTransducer(factory.create(MathElement.FUNCTION)));
        registerTransducer(FINISH, Transducer.autoTransition());
    }
}