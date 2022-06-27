package src.operand;



import src.fsm.FiniteStateMachine;
import src.fsm.TransitionMatrix;
import src.ProgramMemory;
import src.impl.ShuntingYardTransducer;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;

import java.util.function.BiConsumer;

public class OperandMachine extends FiniteStateMachine<Object, ProgramMemory> {

    public static FiniteStateMachine<Object, ProgramMemory> create(MathElementResolverFactory factory){

        BiConsumer<ProgramMemory, Double> consumer = ProgramMemory::pushOperand;

        return FiniteStateMachine.oneOfMachine(new ShuntingYardTransducer<>(factory.create(MathElement.NUMBER), consumer),
                new ShuntingYardTransducer<>(factory.create(MathElement.BRACKETS), consumer),
                new ShuntingYardTransducer<>(factory.create(MathElement.FUNCTION), consumer),
                new VariableTransducer());
    }

    private OperandMachine(TransitionMatrix<Object> matrix, MathElementResolverFactory factory) {
        super(matrix, true);
    }
}
