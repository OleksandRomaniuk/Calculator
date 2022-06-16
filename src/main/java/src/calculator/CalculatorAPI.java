package src.calculator;

import src.calculator.impl.MathematicalExpression;
import src.calculator.impl.Result;
/**
 * Basic interface for math expressions calculation
 */
public interface CalculatorAPI {

    Result calculate(MathematicalExpression expression) throws ExpressionException;
}














