package src.calculator.impl.fsm.operand;

import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.calculator.impl.ShuntingYardTransducer;
import src.calculator.impl.fsm.function.FunctionTransducer;
import src.calculator.impl.fsm.util.ShuntingYardStack;
import src.calculator.impl.math.MathElement;
import src.calculator.impl.math.MathElementResolverFactory;

import java.util.function.BiConsumer;

import static src.calculator.impl.fsm.operand.OperandStates.*;
/**
 * NumberStateMachine is a realisation of {@link FiniteStateMachine}
 * that implements a list of all possible transitions and actions in a certain state when reading operand
 *
 */

public final class OperandMachine extends FiniteStateMachine<Object, ShuntingYardStack> {

    public static FiniteStateMachine<Object, ShuntingYardStack> create(MathElementResolverFactory factory) {

        BiConsumer<ShuntingYardStack, Double> consumer = ShuntingYardStack::pushOperand;

        return FiniteStateMachine.oneOfMachine(new NumberTransducer(factory.create(MathElement.NUMBER)),
                new ShuntingYardTransducer<>(factory.create(MathElement.BRACKETS), consumer),
                new FunctionTransducer(factory.create(MathElement.FUNCTION)));
    }

    private OperandMachine(TransitionMatrix<Object> matrix, MathElementResolverFactory factory) {
        super(matrix, true);
    }
}