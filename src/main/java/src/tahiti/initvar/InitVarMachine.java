package src.tahiti.initvar;


import src.calculator.impl.math.MathElement;
import src.calculator.impl.math.MathElementResolverFactory;
import src.fsm.FiniteStateMachine;
import src.fsm.Transducer;
import src.fsm.TransitionMatrix;
import src.tahiti.VariableHolder;

public final class InitVarMachine extends FiniteStateMachine<InitVarStates, VariableHolder> {

    private InitVarMachine(TransitionMatrix<InitVarStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(InitVarStates.START, Transducer.illegalTransition());
        registerTransducer(InitVarStates.ASSIGN, Transducer.checkAndPassChar('='));
        registerTransducer(InitVarStates.NAME, new variableNameTransducer());
        registerTransducer(InitVarStates.EXPRESSION, new variableExpressionTransducer(factory.create(MathElement.EXPRESSION)));
        registerTransducer(InitVarStates.FINISH, Transducer.autoTransition());
    }

    public static InitVarMachine create(MathElementResolverFactory factory){
        TransitionMatrix<InitVarStates> matrix = TransitionMatrix.<InitVarStates>builder()
                .withStartState(InitVarStates.START)
                .withFinishState(InitVarStates.FINISH)
                .allowTransition(InitVarStates.START, InitVarStates.NAME)
                .allowTransition(InitVarStates.NAME, InitVarStates.ASSIGN)
                .allowTransition(InitVarStates.ASSIGN, InitVarStates.EXPRESSION)
                .allowTransition(InitVarStates.EXPRESSION, InitVarStates.FINISH)

                .build();

        return new InitVarMachine(matrix, factory);
    }
}
