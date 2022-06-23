package src.impl.fsm.function;


import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.TransitionMatrix;
import src.impl.fsm.expression.ExpressionFunctionTransducer;
import src.impl.fsm.util.FunctionHolder;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElement;
import src.impl.math.MathElementResolverFactory;


/**
 *
 *   FunctionMachine is a realisation of {@link FiniteStateMachine}
 *   that implements a list of all possible transitions and actions when reading an funktion
 *
 *
 */

public final class FunctionMachine extends FiniteStateMachine<FunctionStates, FunctionHolder> {

    FunctionFactory functionFactory = new FunctionFactory();

    public static FunctionMachine create(MathElementResolverFactory factory){

        TransitionMatrix<FunctionStates> matrix = TransitionMatrix.<FunctionStates>builder()
                .withStartState(FunctionStates.START)
                .withFinishState(FunctionStates.FINISH)
                .allowTransition(FunctionStates.START, FunctionStates.IDENTIFIER)
                .allowTransition(FunctionStates.IDENTIFIER, FunctionStates.OPENING_BRACKET)
                .allowTransition(FunctionStates.OPENING_BRACKET, FunctionStates.CLOSING_BRACKET, FunctionStates.EXPRESSION)
                .allowTransition(FunctionStates.EXPRESSION, FunctionStates.SEPARATOR, FunctionStates.CLOSING_BRACKET)
                .allowTransition(FunctionStates.SEPARATOR, FunctionStates.EXPRESSION)
                .allowTransition(FunctionStates.CLOSING_BRACKET, FunctionStates.FINISH)

                .build();

        return new FunctionMachine(matrix, factory);
    }


    private FunctionMachine(TransitionMatrix<FunctionStates> matrix, MathElementResolverFactory factory) {
        super(matrix, true);

        registerTransducer(FunctionStates.START, Transducer.illegalTransition());
        registerTransducer(FunctionStates.FINISH, Transducer.autoTransition());
        registerTransducer(FunctionStates.OPENING_BRACKET, Transducer.<FunctionHolder>checkAndPassChar('(').and((inputChain, outputChain) -> {

            String functionName = outputChain.getFunctionName();

            if (!functionFactory.hasFunction(functionName)){
                throw new ResolvingException("Unknown function: " + functionName);
            }

            return true;
        }));registerTransducer(FunctionStates.CLOSING_BRACKET, Transducer.checkAndPassChar(')'));
        registerTransducer(FunctionStates.SEPARATOR, Transducer.checkAndPassChar(','));
        registerTransducer(FunctionStates.IDENTIFIER, new FunctionNameTransducer());
        registerTransducer(FunctionStates.EXPRESSION, new ExpressionFunctionTransducer(factory.create(MathElement.EXPRESSION)));
    }
}
