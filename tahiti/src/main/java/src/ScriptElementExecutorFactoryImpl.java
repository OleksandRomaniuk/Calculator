package src;

import com.google.common.base.Preconditions;
import src.executors.*;
import src.expression.ScriptExpressionMachine;
import src.fsm.brackets.BracketsMachine;
import src.fsm.expression.ExpressionMachine;
import src.fsm.function.FunctionMachine;
import src.fsm.number.NumberStateMachine;
import src.identifier.IdentifierMachine;
import src.initvar.InitVarContext;
import src.initvar.InitVarMachine;
import src.logicaloperand.LogicalOperandMachine;
import src.operators.BinaryOperatorFactory;
import src.operators.DoubleBinaryOperatorFactory;
import src.operators.LogicalBinaryOperatorFactory;
import src.program.ProgramMachine;
import src.type.Value;
import src.util.*;
import src.whileoperator.WhileOperatorExecutor;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

class ScriptElementExecutorFactoryImpl implements ScriptElementExecutorFactory {

    private final Map<ScriptElement, ScriptElementExecutorCreator> executors = new EnumMap<>(ScriptElement.class);

    ScriptElementExecutorFactoryImpl() {

        executors.put(ScriptElement.NUMBER, () -> (inputChain, output) -> {

            Optional<Value> execute = NumberStateMachine.execute(inputChain, errorMessage -> {
                throw new ExecutionException(errorMessage);
            });

            if (execute.isPresent()) {

                if (output.isParseOnly()) {
                    return true;
                }

                output.systemStack().current().pushOperand(execute.get());

                return true;
            }

            return false;
        });

        BinaryOperatorFactory doubleOperatorFactory = new DoubleBinaryOperatorFactory();

        executors.put(ScriptElement.NUMERIC_EXPRESSION, () ->

                new DetachedShuntingYardExecutor<>(ExpressionMachine.create(
                        (scriptContext, abstractBinaryOperator) -> {
                            if (!scriptContext.isParseOnly()) {
                                scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                            }
                        }, doubleOperatorFactory,
                        new ExecutorProgramElementTransducer(ScriptElement.OPERAND, this).named("Operand"),
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        })));

        BinaryOperatorFactory logicalOperatorFactory = new LogicalBinaryOperatorFactory();

        executors.put(ScriptElement.LOGICAL_EXPRESSION, () ->
                new NoSpecialActionExecutor<>(
                        ExpressionMachine.create(
                                (scriptContext, abstractBinaryOperator) -> {
                                    if (!scriptContext.isParseOnly()) {
                                        scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                                    }
                                }, logicalOperatorFactory,
                                new ExecutorProgramElementTransducer(ScriptElement.LOGICAL_EXPRESSION_OPERAND, this)
                                        .named("Operand in logical expression"),
                                errorMessage -> {
                                    throw new ExecutionException(errorMessage);
                                }
                        )));

        executors.put(ScriptElement.LOGICAL_EXPRESSION_OPERAND, () -> new NoSpecialActionExecutor<>(
                LogicalOperandMachine.create(this, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                })
        ));

        executors.put(ScriptElement.RELATIONAL_EXPRESSION, () ->
                new RelationalExpressionElementExecutor(this));

        executors.put(ScriptElement.EXPRESSION, () -> new NoSpecialActionExecutor<>(
                ScriptExpressionMachine.create(this, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                })
        ));

        executors.put(ScriptElement.OPERAND, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.FUNCTION, this).named("Function"),
                        new ExecutorProgramElementTransducer(ScriptElement.BRACKETS, this).named("Brackets"),
                        new ExecutorProgramElementTransducer(ScriptElement.NUMBER, this).named("Number"),
                        new ExecutorProgramElementTransducer(ScriptElement.READ_VARIABLE, this).named("Read variable"))));

        executors.put(ScriptElement.BRACKETS, () -> new NoSpecialActionExecutor<>(
                BracketsMachine.create(new ExecutorProgramElementTransducer(ScriptElement.NUMERIC_EXPRESSION, this)
                                .named("Numeric Expression"),
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        })));

        executors.put(ScriptElement.FUNCTION, () -> new FunctionExecutor(
                new FunctionFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::setArgument, this, ScriptElement.NUMERIC_EXPRESSION)
                                .named("Function"),
                        FunctionHolderWithContext::setFunctionName,
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))));

        executors.put(ScriptElement.INIT_VAR, () -> (inputChain, output) -> {

            InitVarContext initVarContext = new InitVarContext(output);

            InitVarMachine initVarMachine = InitVarMachine.create(this, errorMessage -> {
                throw new ExecutionException(errorMessage);
            });

            return initVarMachine.run(inputChain, initVarContext);
        });

        executors.put(ScriptElement.READ_VARIABLE, () -> (inputChain, context) -> {

            StringBuilder variableName = new StringBuilder();

            IdentifierMachine<ExecutionException> nameMachine = IdentifierMachine.create(errorMessage -> {
                throw new ExecutionException(errorMessage);
            });

            if (nameMachine.run(inputChain, variableName)) {

                if (context.hasVariable(variableName.toString())) {

                    if (context.isParseOnly()) {
                        return true;
                    }

                    Value variable = context.memory().getVariable(variableName.toString());

                    context.systemStack().current().pushOperand(variable);
                    return true;
                } else throw new ExecutionException("Not existing variable in memory " + variableName);
            }
            return false;

        });

        executors.put(ScriptElement.PROCEDURE, () -> new FunctionExecutor(
                new ProcedureFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::setArgument, this, ScriptElement.EXPRESSION),
                        FunctionHolderWithContext::setFunctionName,
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))));

        executors.put(ScriptElement.STATEMENT, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ScriptElement.INIT_VAR, this).named("Variable initialisation"),
                        new ExecutorProgramElementTransducer(ScriptElement.WHILE_OPERATOR, this).named("While loop"),
                        new ExecutorProgramElementTransducer(ScriptElement.PROCEDURE, this).named("Procedure"))));

        executors.put(ScriptElement.PROGRAM, () -> new NoSpecialActionExecutor<>(
                ProgramMachine.create(this, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                })
        ));

        executors.put(ScriptElement.WHILE_OPERATOR, () -> new WhileOperatorExecutor(this));
    }

    @Override
    public ScriptElementExecutor create(ScriptElement element) {

        Preconditions.checkNotNull(element);

        return executors.get(element).create();
    }

}
