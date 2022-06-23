package src;


import src.impl.MathematicalExpression;
import src.impl.Result;
import src.impl.fsm.util.ResolvingException;

/**
 * Basic interface for math expressions calculation
 */
public interface CalculatorAPI {

    Result calculate(MathematicalExpression expression) throws ExpressionException;
}














