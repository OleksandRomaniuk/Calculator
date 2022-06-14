package src.impl;

import com.google.common.base.Preconditions;
import src.CalculatorAPI;
import src.ExpressionException;
import src.impl.fsm.calculator.CalculatorMachine;
import src.impl.fsm.util.Input;
import src.impl.fsm.util.ResolvingException;
import src.impl.fsm.util.ShuntingYardStack;
import src.impl.math.MathElementResolverFactory;

import java.util.function.DoubleBinaryOperator;
/**
 * Implementation of {@link CalculatorAPI} interface
 */

public class Calculator implements CalculatorAPI {
    public Result calculate(MathematicalExpression expression) throws ExpressionException {
        Preconditions.checkNotNull(expression);

        MathElementResolverFactory factory = new ResolverFactoryImpl();

        CalculatorMachine numberStateMachine = CalculatorMachine.create(factory);

        Input inputChain = new Input(expression.getExpression());
        ShuntingYardStack outputChain = new ShuntingYardStack();

        try {
            if (!numberStateMachine.run(inputChain, outputChain) || outputChain.peekResult() == infinity()) {

                raiseException(inputChain);
            }
        } catch (ResolvingException e) {
            raiseException(inputChain);
        }

        return new Result(outputChain.peekResult());
    }

    private static void raiseException(Input inputChain) throws ExpressionException {
        throw new ExpressionException("Wrong mathematical expression", inputChain.position());
    }

    private static double infinity() {
        DoubleBinaryOperator divisionByZero = (left, right) -> left / right;
        return divisionByZero.applyAsDouble(1, 0);
    }

}




























