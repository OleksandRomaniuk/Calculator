package src;

import src.impl.MathematicalExpression;
import src.impl.Result;

public interface CalculatorAPI {

    Result calculate(MathematicalExpression expression) throws ExpressionException;
}














