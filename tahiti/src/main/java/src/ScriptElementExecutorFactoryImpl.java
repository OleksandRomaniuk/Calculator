package src;

import com.google.common.base.Preconditions;
import fsm.FiniteStateMachine;
import fsm.ResolvingException;
import fsm.identifier.IdentifierMachine;
import src.executors.*;
import src.impl.fsm.brackets.BracketsMachine;
import src.impl.fsm.expression.ExpressionMachine;
import src.impl.fsm.function.FunctionMachine;
import src.impl.fsm.number.NumberStateMachine;
import src.initvar.InitVarContext;
import src.initvar.InitVarMachine;
import src.program.ProgramMachine;
import src.util.*;


import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

public class ScriptElementExecutorFactoryImpl implements ScriptElementExecutorFactory {

    private final Map<ScriptElement, ScriptElementExecutorCreator> executors = new EnumMap<>(ScriptElement.class);

    ScriptElementExecutorFactoryImpl() {

        ScriptElementExecutorCreator expressionExecutorCreator = () ->
                new DetachedShuntingYardExecutor<>(ExpressionMachine.create(
                        (scriptContext, prioritizedBinaryOperator) ->
                                scriptContext.systemStack().current().pushOperator(prioritizedBinaryOperator),
                        new ExecutorProgramElementTransducer(ScriptElement.OPERAND, this)));

        executors.put(ScriptElement.NUMBER, () -> (inputChain, output) -> {

            Optional<Double> execute = NumberStateMachine.execute(inputChain);

            if (execute.isPresent()) {
                output.systemStack().current().pushOperand(execute.get());

                return true;
            }

            return false;
        });

        executors.put(ScriptElement.EXPRESSION, expressionExecutorCreator);

        executors.put(ScriptElement.OPERAND, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        new ExecutorProgramElementTransducer(ScriptElement.NUMBER, this),
                        new ExecutorProgramElementTransducer(ScriptElement.BRACKETS, this),
                        new ExecutorProgramElementTransducer(ScriptElement.FUNCTION, this),
                        new ExecutorProgramElementTransducer(ScriptElement.READVARIABLE, this))));

        executors.put(ScriptElement.BRACKETS, () -> new NoSpecialActionExecutor<>(
                BracketsMachine.create(new ExecutorProgramElementTransducer(ScriptElement.EXPRESSION, this))));

        executors.put(ScriptElement.FUNCTION, () -> new FunctionExecutor(
                new FunctionFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::setArgument, expressionExecutorCreator.create()),
                        FunctionHolderWithContext::setFunctionName
                ))));

        executors.put(ScriptElement.INITVAR, () -> (inputChain, output) -> {

            InitVarContext initVarContext = new InitVarContext(output);

            InitVarMachine initVarMachine = InitVarMachine.create(this);

            return initVarMachine.run(inputChain, initVarContext);
        });

        executors.put(ScriptElement.READVARIABLE, () -> (inputChain, context) -> {

            StringBuilder variableName = new StringBuilder();

            IdentifierMachine nameMachine = IdentifierMachine.create();

            if (nameMachine.run(inputChain, variableName)) {

                if (context.hasVariable(variableName.toString())) {

                    Double variable = context.memory().getVariable(variableName.toString());

                    context.systemStack().current().pushOperand(variable);
                    return true;
                } else throw new ResolvingException("Not existing variable in memory " + variableName);
            }
            return false;

        });

        executors.put(ScriptElement.PROCEDURE, () -> new FunctionExecutor(
                new ProcedureFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::setArgument, expressionExecutorCreator.create()),
                       FunctionHolderWithContext::setFunctionName
                ))));

        executors.put(ScriptElement.STATEMENT, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        new ExecutorProgramElementTransducer(ScriptElement.INITVAR, this),
                        new ExecutorProgramElementTransducer(ScriptElement.PROCEDURE, this))));

        executors.put(ScriptElement.PROGRAM, () -> new NoSpecialActionExecutor<>(
                ProgramMachine.create(this)
        ));
    }

    @Override
    public ScriptElementExecutor create(ScriptElement element) {

        Preconditions.checkNotNull(element);

        return executors.get(element).create();
    }

}
