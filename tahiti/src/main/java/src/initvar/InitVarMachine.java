package src.initvar;


import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.ProgramMemory;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;

import static src.initvar.InitVarStates.*;

public final class InitVarMachine extends FiniteStateMachine<InitVarStates, InitVarContext> {

    private InitVarMachine(TransitionMatrix<InitVarStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(START, Transducer.illegalTransition());
        registerTransducer(ASSIGN, Transducer.checkAndPassChar('='));
        registerTransducer(NAME, new variableNameTransducer());
        registerTransducer(EXPRESSION, new variableExpressionTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(FINISH, (inputChain, outputChain) -> {

            ProgramMemory memory = new ProgramMemory();

            memory.putVariable(outputChain.getVariableName(), outputChain.getVariableValue());

            return true;
        });
    }

    public static InitVarMachine create(MathElementResolverFactory factory){
        TransitionMatrix<InitVarStates> matrix = TransitionMatrix.<InitVarStates>builder()
                .withStartState(START)
                .withFinishState(FINISH)
                .withTemporaryState(NAME)
                .allowTransition(START, NAME)
                .allowTransition(NAME, ASSIGN)
                .allowTransition(ASSIGN, EXPRESSION)
                .allowTransition(EXPRESSION, FINISH)

                .build();

        return new InitVarMachine(matrix, factory);
    }
}
