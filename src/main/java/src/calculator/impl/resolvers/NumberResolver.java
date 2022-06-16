package src.calculator.impl.resolvers;

import src.calculator.impl.fsm.number.NumberStateMachine;
import src.calculator.impl.fsm.util.Input;
import src.calculator.impl.fsm.util.ResolvingException;
import src.calculator.impl.math.MathElementResolver;

import java.util.Optional;

public class NumberResolver implements MathElementResolver {
    @Override
    public Optional<Double> resolve(Input inputChain) throws ResolvingException {

        StringBuilder stringBuilder = new StringBuilder(32);

        NumberStateMachine numberMachine = NumberStateMachine.create();

        if (numberMachine.run(inputChain, stringBuilder)){

            return Optional.of(Double.parseDouble(stringBuilder.toString()));
        }

        return Optional.empty();
    }
}
