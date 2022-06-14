package src;

import src.impl.MathematicalExpression;
import src.impl.Result;
/**
 * Basic interface for math expressions calculation
 */
public interface CalculatorAPI {

    Result calculate(MathematicalExpression expression) throws ExpressionException;
}














