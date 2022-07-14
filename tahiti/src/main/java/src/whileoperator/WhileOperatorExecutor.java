package src.whileoperator;

import com.google.common.base.Preconditions;
import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import fsm.Transducer;
import fsm.type.BooleanValueVisitor;
import src.runtime.ScriptContext;
import src.util.*;

import java.util.List;

public class WhileOperatorExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutorFactory factory;

    public WhileOperatorExecutor(ScriptElementExecutorFactory factory) {
        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        Transducer<WhileOperatorContext, ExecutionException> keyword = (inputChain14, outputChain) -> {

            outputChain.setPosition(inputChain14.position());

            List<Transducer<WhileOperatorContext, ExecutionException>> keyword1 = Transducer.keyword("while");

            for (Transducer<WhileOperatorContext, ExecutionException> transducer : keyword1) {

                if (!transducer.doTransition(inputChain14, outputChain)) {
                    return false;
                }
            }

            return true;
        };

        Transducer<WhileOperatorContext, ExecutionException> relationTransducer =
                new FunctionTransducer<>((whileOperatorContext, value) -> {
                    whileOperatorContext.setConditionValue(BooleanValueVisitor.read(value));
                }, factory, ScriptElement.RELATIONAL_EXPRESSION);

        Transducer<WhileOperatorContext, ExecutionException> programTransducer =
                (inputChain1, outputChain) -> {

                    ScriptElementExecutor executor = factory.create(ScriptElement.PROGRAM);

                    return executor.execute(inputChain1, outputChain.getScriptContext());
                };

        List<Transducer<WhileOperatorContext, ExecutionException>> transducers = List.of(keyword.named("While keyword"),
                Transducer.<WhileOperatorContext, ExecutionException>checkAndPassChar('(').named("("),
                relationTransducer.named("Relational expression"),
                Transducer.<WhileOperatorContext, ExecutionException>checkAndPassChar(')').named(")")
                        .and((inputChain13, outputChain) -> {

                            if (!outputChain.getConditionValue()) {

                                outputChain.getScriptContext().setParsingPermission(true);
                            }

                            return true;
                        }).named("Check condition of while loop"),
                Transducer.<WhileOperatorContext, ExecutionException>checkAndPassChar('{').named("Opening brace"),
                programTransducer.named("Program state"),
                Transducer.<WhileOperatorContext, ExecutionException>checkAndPassChar('}')
                        .and((inputChain12, outputChain) -> {

                            if (outputChain.getConditionValue()) {

                                inputChain12.setPosition(outputChain.getPosition());

                                ScriptElementExecutor executor = factory.create(ScriptElement.WHILE_OPERATOR);

                                executor.execute(inputChain12, outputChain.getScriptContext());
                            }

                            outputChain.getScriptContext().setParsingPermission(false);

                            return true;
                        }).named("Closing brace"));


        var machine = FiniteStateMachine.chainMachine(errorMessage -> {
            throw new ExecutionException(errorMessage);
        }, List.of(keyword), transducers);

        WhileOperatorContext whileOperatorContext = new WhileOperatorContext(output);

        return machine.run(inputChain, whileOperatorContext);
    }
}
