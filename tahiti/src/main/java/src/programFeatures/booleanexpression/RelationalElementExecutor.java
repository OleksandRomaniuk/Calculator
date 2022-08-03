package src.programFeatures.booleanexpression;

import com.google.common.base.Preconditions;
import src.*;
import src.calucator.fsm.expression.BinaryOperatorTransducer;
import src.operators.BinaryOperatorFactory;
import src.operators.RelationalBinaryOperatorFactory;
import src.runtime.ProgramContext;
import src.util.ProgramElementTransducer;
import src.tahiti.*;

import java.util.List;

/**
 * Implementation of {@link ProgramElementExecutor}
 * that used to create and run {@link FiniteStateMachine} for parsing relational expressions.
 */

public class RelationalElementExecutor implements ProgramElementExecutor {

    private final ProgramFactory factory;

    public RelationalElementExecutor(ProgramFactory scriptElementExecutorFactory) {
        this.factory = Preconditions.checkNotNull(scriptElementExecutorFactory);
    }

    @Override
    public boolean execute(CharSequenceReader inputChain, ProgramContext output) throws ExecutionException {

        BinaryOperatorFactory relationalOperatorFactory = new RelationalBinaryOperatorFactory();

        var partOfExpression =
                new ProgramElementTransducer(ProgramElement.NUMERIC_EXPRESSION, factory).named("Part Of Relational Expression");

        var relationalExpressionMachine =
                FiniteStateMachine.chainMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        List.of(partOfExpression),

                        List.of(partOfExpression,
                                new BinaryOperatorTransducer<ProgramContext, ExecutionException>(
                                        relationalOperatorFactory,
                                        (scriptContext, abstractBinaryOperator) -> {
                                            if (!scriptContext.isParseOnly()) {

                                                scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                                            }
                                        }).named("Binary operator"), partOfExpression
                        )
                );

        if (!relationalExpressionMachine.run(inputChain, output)) {

            output.memory().clearCache();

            output.memory().backup();

            return false;
        }

        return true;
    }
}
