package src.executors;

import src.BinaryOperatorFactory;
import src.CharSequenceReader;
import src.FiniteStateMachine;
import src.RelationalBinaryOperatorFactory;
import src.fsm.expression.BinaryOperatorTransducer;
import src.runtime.ScriptContext;
import src.util.*;

import java.util.List;

public class RelationalExpressionElementExecutor implements ProgramElementExecutor {

    private final ProgramFactory factory;

    public RelationalExpressionElementExecutor(ProgramFactory programFactory) {
        this.factory = programFactory;
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ScriptContext output) throws ExecutionException {

        BinaryOperatorFactory relationalOperatorFactory = new RelationalBinaryOperatorFactory();

        var partOfExpression = new ExecutorProgramElementTransducer(ProgramElement.NUMERIC_EXPRESSION, factory)
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
                        new ExecutorProgramElementTransducer(ProgramElement.NUMERIC_EXPRESSION, factory)));

        return relationalMachine.run(inputChain, output);
    }
}
