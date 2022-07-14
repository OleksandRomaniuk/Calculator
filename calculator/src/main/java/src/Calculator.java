package src;

import com.google.common.base.Preconditions;
import src.fsm.ShuntingYard;
import src.fsm.calculator.CalculatorMachine;
import src.math.MathElementResolverFactory;
import src.type.DoubleValueVisitor;

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

public class Calculator {
    public CalculationResult calculate(MathematicalExpression expression) throws WrongExpressionException {
        Preconditions.checkNotNull(expression);

        MathElementResolverFactory factory = new MathElementResolverFactoryImpl();

        var numberStateMachine = CalculatorMachine.create(factory, errorMessage -> {
            throw new ResolvingException(errorMessage);
        });

        var inputChain = new CharSequenceReader(expression.getExpression());
        var outputChain = new ShuntingYard();

        try {
            if (!numberStateMachine.run(inputChain, outputChain)) {

                raiseException(inputChain);
            }
        } catch (ResolvingException e) {
            raiseException(inputChain);
        }

        return new CalculationResult(DoubleValueVisitor.read(outputChain.popResult()));
    }

    private static void raiseException(CharSequenceReader inputChain) throws WrongExpressionException {
        throw new WrongExpressionException("Wrong mathematical expression", inputChain.position());
    }

}




























