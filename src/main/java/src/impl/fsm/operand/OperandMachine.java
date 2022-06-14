package src.impl.fsm.operand;

import src.impl.fsm.FiniteStateMachine;
import src.impl.fsm.Transducer;
import src.impl.fsm.TransitionMatrix;
import src.impl.ShuntingYardTransducer;
import src.impl.fsm.function.FunctionTransducer;
import src.impl.fsm.util.ShuntingYardStack;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;

import static src.impl.fsm.operand.OperandStates.*;


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