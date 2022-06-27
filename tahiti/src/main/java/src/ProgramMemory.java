package src;


import src.impl.fsm.util.ShuntingYard;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.DoubleBinaryOperator;

public class ProgramMemory extends ShuntingYard {

    private final StringBuilder output = new StringBuilder();

    private static final Map<String, Double> variables = new TreeMap<>();



    private final Deque<Double> operandStack = new ArrayDeque<>();

    private final Deque<PrioritizedBinaryOperator> operatorStack = new ArrayDeque<>();

    public void setOutput(String output) {
        this.output.append(output);
    }

    StringBuilder getOutput() {
        return output;
    }

    @Override
    public void pushOperand(double operand) {

        operandStack.push(operand);
    }

    @Override
    public void pushOperator(PrioritizedBinaryOperator operator) {

        while (!operatorStack.isEmpty() && operatorStack.peek().compareTo(operator) >= 0) {

            actionTopOperator();
        }

        operatorStack.push(operator);
    }

    @Override
    public double peekResult() {

        while (!operatorStack.isEmpty()) {

            actionTopOperator();
        }



        assert operandStack.peek() != null;
        return operandStack.peek();
    }


    private void actionTopOperator() {
        Double rightOperand = operandStack.pop();

        Double leftOperand = operandStack.pop();

        DoubleBinaryOperator operator = operatorStack.pop();

        double result = operator.applyAsDouble(leftOperand, rightOperand);

        operandStack.push(result);
    }

    @Override
    public double peekOperand() {
        assert operandStack.peek() != null;

    }

    @Override
    public PrioritizedBinaryOperator peekOperator() {
        assert operatorStack.peek() != null;
        return operatorStack.peek();
    }


    public void putVariable(String name, Double value){



        variables.put(name, value);
    }

    public Double getVariable(String name){

        return variables.get(name);
    }

    public boolean hasVariable(String name){
        return variables.containsKey(name);
    }
}
