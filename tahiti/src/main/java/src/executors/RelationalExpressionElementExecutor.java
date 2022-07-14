package src.executors;

import fsm.CharSequenceReader;
import fsm.FiniteStateMachine;
import src.BinaryOperatorFactory;
import src.ExecutorProgramElementTransducer;
import src.RelationalBinaryOperatorFactory;
import src.fsm.expression.BinaryOperatorTransducer;
import src.runtime.ScriptContext;
import src.util.ExecutionException;
import src.util.ScriptElement;
import src.util.ScriptElementExecutor;
import src.util.ScriptElementExecutorFactory;

import java.util.List;

public class RelationalExpressionElementExecutor implements ScriptElementExecutor {

    private final ScriptElementExecutorFactory factory;

    public RelationalExpressionElementExecutor(ScriptElementExecutorFactory scriptElementExecutorFactory) {
        this.factory = scriptElementExecutorFactory;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        BinaryOperatorFactory relationalOperatorFactory = new RelationalBinaryOperatorFactory();

        var partOfExpression = new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, factory)
                .named("Part Of Relational Expression");

        var relationalMachine = FiniteStateMachine.chainMachine(errorMessage -> {
                    throw new ExecutionException(errorMessage);
                },
                List.of(partOfExpression),

                List.of(partOfExpression,
                        new BinaryOperatorTransducer<ScriptContext, ExecutionException>(
                                relationalOperatorFactory,
                                (scriptContext, abstractBinaryOperator) -> {
                                    if (!scriptContext.isParseOnly()) {
                                        scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                                    }
                                }).named("Binary operator"),
                        new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, factory)));

        return relationalMachine.run(inputChain, output);
    }
}
