package src;

import com.google.common.base.Preconditions;
import src.executors.*;
import src.fsm.brackets.BracketsMachine;
import src.fsm.expression.ExpressionMachine;
import src.fsm.function.FunctionMachine;
import src.fsm.number.NumberStateMachine;
import src.identifier.IdentifierMachine;
import src.program.ProgramMachine;
import src.programStructure.booleanOperand.BooleanOperandMachine;
import src.programStructure.initvar.InitVarContext;
import src.programStructure.initvar.InitVarMachine;
import src.programStructure.switchoperator.SwitchContext;
import src.programStructure.switchoperator.SwitchOperatorMachine;
import src.programStructure.ternary.TernaryOperatorContext;
import src.programStructure.ternary.TernaryOperatorMachine;
import src.programStructure.whileoperator.WhileOperatorExecutor;
import src.type.DoubleValueVisitor;
import src.type.Value;
import src.util.*;

import java.util.EnumMap;
import java.util.Map;
import java.util.Optional;

class ProgramExecutorFactoryImpl implements ProgramFactory {

    private final Map<ProgramElement, ProgramElementExecutorCreator> executors = new EnumMap<>(ProgramElement.class);

    ProgramExecutorFactoryImpl() {

        executors.put(ProgramElement.NUMBER, () -> (inputChain, output) -> {

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

        BinaryOperatorFactory doubleBiOperatorFactory = new DoubleBinaryOperatorFactory();

        ExceptionThrower<ExecutionException> exceptionThrower = errorMessage -> {
            throw new ExecutionException(errorMessage);
        };


        executors.put(ProgramElement.NUMERIC_EXPRESSION, () ->
                new DetachedShuntingYardExecutor<>(ExpressionMachine.create(
                        (scriptContext, abstractBinaryOperator) -> {
                            if (!scriptContext.isParseOnly()) {
                                scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                            }
                        }, doubleBiOperatorFactory,
                        new ExecutorProgramElementTransducer(ProgramElement.OPERAND, this).named("Operand").and(
                                (inputChain, outputChain) ->
                                        DoubleValueVisitor.isDouble(outputChain.systemStack().current().peekOperand())
                        ),
                        exceptionThrower)
                )
        );

        executors.put(ProgramElement.RELATIONAL_EXPRESSION, () ->
                new RelationalExpressionElementExecutor(this)
        );

        executors.put(ProgramElement.EXPRESSION, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        exceptionThrower,
                        new ExecutorProgramElementTransducer(ProgramElement.BOOLEAN_EXPRESSION, this).named("Boolean operator"),
                        new ExecutorProgramElementTransducer(ProgramElement.TERNARY_EXPRESSION, this).named("Ternary operator"),
                        new ExecutorProgramElementTransducer(ProgramElement.NUMERIC_EXPRESSION, this).named("Numeric expression")
                )));
        BinaryOperatorFactory logicalOperatorFactory = new BooleanBinaryOperatorFactory();


        executors.put(ProgramElement.BOOLEAN_OPERAND, () -> new NoSpecialActionExecutor<>(
                BooleanOperandMachine.create(this, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                })
        ));

        executors.put(ProgramElement.BOOLEAN_EXPRESSION, () ->
                new NoSpecialActionExecutor<>(
                        ExpressionMachine.create(
                                (scriptContext, abstractBinaryOperator) -> {
                                    if (!scriptContext.isParseOnly()) {
                                        scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                                    }
                                }, logicalOperatorFactory,
                                new ExecutorProgramElementTransducer(ProgramElement.BOOLEAN_OPERAND, this)
                                        .named("Operand in logical expression"),
                                errorMessage -> {
                                    throw new ExecutionException(errorMessage);
                                }
                        )));

        executors.put(ProgramElement.RELATIONAL_EXPRESSION, () ->
                new RelationalExpressionElementExecutor(this));



        executors.put(ProgramElement.OPERAND, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ProgramElement.FUNCTION, this).named("Function"),
                        new ExecutorProgramElementTransducer(ProgramElement.BRACKETS, this).named("Brackets"),
                        new ExecutorProgramElementTransducer(ProgramElement.NUMBER, this).named("Number"),
                        new ExecutorProgramElementTransducer(ProgramElement.READ_VARIABLE, this).named("Read variable"))));

        executors.put(ProgramElement.BRACKETS, () -> new NoSpecialActionExecutor<>(
                BracketsMachine.create(new ExecutorProgramElementTransducer(ProgramElement.NUMERIC_EXPRESSION, this)
                                .named("Numeric Expression"),
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        })));

        executors.put(ProgramElement.FUNCTION, () -> new FunctionExecutor(
                new FunctionFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::setArgument, this, ProgramElement.NUMERIC_EXPRESSION)
                                .named("Function"),
                        FunctionHolderWithContext::setFunctionName,
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))));

        executors.put(ProgramElement.INIT_VAR, () -> (inputChain, output) -> {

            InitVarContext initVarContext = new InitVarContext(output);

            InitVarMachine initVarMachine = InitVarMachine.create(this, errorMessage -> {
                throw new ExecutionException(errorMessage);
            });

            return initVarMachine.run(inputChain, initVarContext);
        });

        executors.put(ProgramElement.READ_VARIABLE, () -> (inputChain, context) -> {

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
        executors.put(ProgramElement.SWITCH_OPERATOR, () -> (inputChain, output) -> {

            SwitchOperatorMachine switchOperatorMachine = SwitchOperatorMachine.create(this, errorMessage -> {
                throw new ExecutionException(errorMessage);
            });

            SwitchContext switchContext = new SwitchContext(output);

            return switchOperatorMachine.run(inputChain, switchContext);
        });

        executors.put(ProgramElement.TERNARY_EXPRESSION, () -> (inputChain, output) -> {

            var ternaryOperatorMachine = TernaryOperatorMachine.create(this, errorMessage -> {
                throw new ExecutionException(errorMessage);
            });

            var ternaryOperatorContext = new TernaryOperatorContext(output);

            return ternaryOperatorMachine.run(inputChain, ternaryOperatorContext);
        });

        executors.put(ProgramElement.PROCEDURE, () -> new FunctionExecutor(
                new ProcedureFactoryExecutor<>(FunctionMachine.create(
                        new FunctionTransducer<>(FunctionHolderWithContext::setArgument, this, ProgramElement.EXPRESSION),
                        FunctionHolderWithContext::setFunctionName,
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))));

        executors.put(ProgramElement.STATEMENT, () -> new NoSpecialActionExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ExecutorProgramElementTransducer(ProgramElement.WHILE_OPERATOR, this).named("While loop"),
                        new ExecutorProgramElementTransducer(ProgramElement.SWITCH_OPERATOR, this),
                        new ExecutorProgramElementTransducer(ProgramElement.INIT_VAR, this).named("Variable initialisation"),
                        new ExecutorProgramElementTransducer(ProgramElement.PROCEDURE, this).named("Procedure"))));


        executors.put(ProgramElement.PROGRAM, () -> new NoSpecialActionExecutor<>(
                ProgramMachine.create(this, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                })
        ));

        executors.put(ProgramElement.WHILE_OPERATOR, () -> new WhileOperatorExecutor(this));
    }

    @Override
    public ProgramElementExecutor create(ProgramElement element) {

        Preconditions.checkNotNull(element);

        return executors.get(element).create();
    }

}
