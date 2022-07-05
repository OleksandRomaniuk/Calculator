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

        var keyword = (Transducer<WhileOperatorContext, ExecutionException>) (inputChain12, outputChain) -> {

            outputChain.setPosition(inputChain12.position());

            List<Transducer<WhileOperatorContext, ExecutionException>> keyword1 = Transducer.keyword("while");

            for (var transducer : keyword1) {

                if (!transducer.doTransition(inputChain12, outputChain)) {
                    return false;
                }
            }

            return true;
        };

        Transducer<WhileOperatorContext, ExecutionException> relationTransducer =
                new FunctionTransducer<>((whileOperatorContext, value) -> {
                    whileOperatorContext.setCondition(BooleanValueVisitor.read(value));
                },
                        factory, ScriptElement.RELATIONAL_EXPRESSION);

        var programTransducer =
                (Transducer<WhileOperatorContext, ExecutionException>) (inputChain1, outputChain) -> {

                    var executor = factory.create(ScriptElement.PROGRAM);

                    return executor.execute(inputChain1, outputChain.getScriptContext());
                };

        List<Transducer<WhileOperatorContext, ExecutionException>> transducers = List.of(keyword,
                Transducer.checkAndPassChar('('),
                relationTransducer,
                Transducer.<WhileOperatorContext, ExecutionException>checkAndPassChar(')')
                        .and((inputChain13, outputChain) -> {

                            if (!outputChain.isCondition()) {

                                outputChain.getScriptContext().setParsingPermission(true);
                            }

                            return true;
                        }),
                Transducer.checkAndPassChar('{'),
                programTransducer,
                Transducer.<WhileOperatorContext, ExecutionException>checkAndPassChar('}')
                        .and(new Transducer<WhileOperatorContext, ExecutionException>() {
                            @Override
                            public boolean doTransition(CharSequenceReader inputChain, WhileOperatorContext outputChain) throws ExecutionException {

                                if (outputChain.isCondition()) {

                                    inputChain.setPosition(outputChain.getPosition());

                                    ScriptElementExecutor executor = factory.create(ScriptElement.WHILE_OPERATOR);

                                    executor.execute(inputChain, outputChain.getScriptContext());
                                }

                                outputChain.getScriptContext().setParsingPermission(false);

                                return true;
                            }
                        }));


        var machine = FiniteStateMachine.chainMachine(errorMessage -> {
            throw new ExecutionException(errorMessage);
        }, List.of(keyword), transducers);

        var whileOperatorContext = new WhileOperatorContext(output);

        return machine.run(inputChain, whileOperatorContext);
    }
}
