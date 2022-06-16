package src.calculator.impl;

import com.google.common.base.Preconditions;
import src.calculator.CalculatorAPI;
import src.calculator.ExpressionException;
import src.calculator.impl.fsm.calculator.CalculatorMachine;
import src.calculator.impl.fsm.util.Input;
import src.calculator.impl.fsm.util.ResolvingException;
import src.calculator.impl.fsm.util.ShuntingYardStack;
import src.calculator.impl.math.MathElementResolverFactory;

import java.util.function.DoubleBinaryOperator;

/**
 * An API for resolving of math expressions. Math expression may contain:
 * - integer and float numbers;
 * - binary operators with priorities, e.g. +, -, *, /, ^ (power operator);
 * - brackets;
 * - functions, e.g. sum, min, max, log, log2, log10, sqrt, pi.
 * Raises a specific error if math expression is incorrect.
 *
 * <p>Typical usage scenario:
 * <p>
 * {@code
 * <p>
 * MathExpression expression = new MathExpression("2+2*3");
 * Calculator src.calculator = new Calculator();
 * Result result = src.calculator.calculate(expression);
 * <p>
 * }
 *
 *
 * <p> Implementation details: uses a set of finite state machines in order to
 * define grammar of math expression, parse elements of expression and calculate the result.
 *
 * @author Oleksandr Romaniuk
 * @version 0.9 beta
 */

public class CalculatorAPIimpl implements CalculatorAPI {
    public src.calculator.impl.Result calculate(src.calculator.impl.MathematicalExpression expression) throws ExpressionException {
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




























