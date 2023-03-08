package src;

import com.google.common.base.Preconditions;

import src.calucator.fsm.function.FunctionExecutor;
import src.calucator.fsm.number.NumberParsingExecutor;
import src.operators.BooleanBinaryOperatorFactory;
import src.operators.DoubleBinaryOperatorFactory;
import src.operators.StringBinaryOperatorFactory;
import src.program.MachineExecutor;
import src.calucator.fsm.brackets.BracketsMachine;
import src.calucator.fsm.expression.ExpressionMachine;
import src.calucator.fsm.function.FunctionMachine;
import src.programFeatures.forloop.ForLoopExecutor;
import src.programFeatures.varopertor.InitVarExecutor;
import src.programFeatures.booleanexpression.BooleanOperandMachine;
import src.program.ProgramMachine;
import src.programFeatures.booleanexpression.RelationalElementExecutor;
import src.programFeatures.procedure.ProcedureExecutor;
import src.programFeatures.switchoperator.SwitchOperatorExecutor;
import src.programFeatures.ternary.TernaryOperatorExecutor;
import src.programFeatures.variable.ProduceVariableExecutor;
import src.programFeatures.variable.ReadVariableExecutor;
import src.programFeatures.whileoperator.WhileOperatorExecutor;
import src.programFeatures.string.StringOperandTransducer;
import src.util.DetachedShuntingYardExecutor;
import src.util.FunctionHolderWithContext;
import src.util.ProgramElementTransducer;
import src.calucator.fsm.expression.ExpressionTransducer;
import src.tahiti.*;

import java.util.EnumMap;
import java.util.Map;

/**
 * Implementation of {@link ProgramFactory} that create {@link ProgramElementExecutorCreator}
 */

class ProgramFactoryImpl implements ProgramFactory {

    private final Map<ProgramElement, ProgramElementExecutorCreator> executors = new EnumMap<>(ProgramElement.class);

    ProgramFactoryImpl() {


        executors.put(ProgramElement.NUMBER, NumberParsingExecutor::new);

        executors.put(ProgramElement.BRACKETS, () -> new MachineExecutor<>(
                        BracketsMachine.create(
                                new ProgramElementTransducer(ProgramElement.NUMERIC_EXPRESSION, this),
                                errorMessage -> {
                                    throw new ExecutionException(errorMessage);
                                }
                        )
                )
        );

        executors.put(ProgramElement.FUNCTION, () ->
                new FunctionExecutor<>(FunctionMachine.create(
                        Transducer.checkAndPassChar('('),

                        FunctionHolderWithContext::setFunctionName,

                        new ExpressionTransducer<>(FunctionHolderWithContext::addArgument,
                                this,
                                ProgramElement.NUMERIC_EXPRESSION).named("Expression inside function"),

                        Transducer.checkAndPassChar(')'),

                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))
        );
        executors.put(ProgramElement.OPERAND, () -> new MachineExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ProgramElementTransducer(ProgramElement.FUNCTION, this).named("Function operand"),
                        new ProgramElementTransducer(ProgramElement.BRACKETS, this).named("Brackets operand"),
                        new ProgramElementTransducer(ProgramElement.ANALYZE_VARIABLE, this).named("Variable as operand"),
                        new ProgramElementTransducer(ProgramElement.NUMBER, this).named("Number operand")
                )
        ));


        executors.put(ProgramElement.EXPRESSION, () -> new MachineExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                       new ProgramElementTransducer(ProgramElement.TERNARY_OPERATOR, this).named("Ternary operator"),
                        new ProgramElementTransducer(ProgramElement.BOOLEAN_EXPRESSION, this).named("Logical expression"),
                        new ProgramElementTransducer(ProgramElement.NUMERIC_EXPRESSION, this).named("Numeric expression"),
                        new ProgramElementTransducer(ProgramElement.STRING_EXPRESSION, this).named("String expression")
                )
        ));

        executors.put(ProgramElement.NUMERIC_EXPRESSION, () ->
                new DetachedShuntingYardExecutor<>(ExpressionMachine.create(
                        (scriptContext, abstractBinaryOperator) -> {
                            if (!scriptContext.isParseOnly()) {
                                scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                            }
                        }, new DoubleBinaryOperatorFactory(),
                        new ProgramElementTransducer(ProgramElement.OPERAND, this).named("Operand"),
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        })
                )
        );
        executors.put(ProgramElement.RELATIONAL_EXPRESSION, () ->
                new RelationalElementExecutor(this)
        );

        executors.put(ProgramElement.STRING_EXPRESSION, () -> new MachineExecutor<>(
                ExpressionMachine.create(
                        (scriptContext, abstractBinaryOperator) -> {
                            if (!scriptContext.isParseOnly()) {
                                scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                            }
                        }, new StringBinaryOperatorFactory(),
                        new StringOperandTransducer(this), errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                )
        ));

        executors.put(ProgramElement.BOOLEAN_EXPRESSION, () ->
                new MachineExecutor<>(
                        ExpressionMachine.create(
                                (scriptContext, abstractBinaryOperator) -> {
                                    if (!scriptContext.isParseOnly()) {
                                        scriptContext.systemStack().current().pushOperator(abstractBinaryOperator);
                                    }
                                },
                                new BooleanBinaryOperatorFactory(),
                                new ProgramElementTransducer(ProgramElement.BOOLEAN_OPERAND, this)
                                        .named("Operand in logical expression"),
                                errorMessage -> {
                                    throw new ExecutionException(errorMessage);
                                }
                        )));

        executors.put(ProgramElement.BOOLEAN_OPERAND, () -> new MachineExecutor<>(
                BooleanOperandMachine.create(this, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                })
        ));


        executors.put(ProgramElement.PROCEDURE, () ->
                new ProcedureExecutor<>(FunctionMachine.create(
                        Transducer.checkAndPassChar('('),

                        FunctionHolderWithContext::setFunctionName,

                        new ExpressionTransducer<>(FunctionHolderWithContext::addArgument,
                                this,
                                ProgramElement.EXPRESSION),

                        Transducer.checkAndPassChar(')'),

                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        }
                ))
        );

        executors.put(ProgramElement.STATEMENT, () -> new MachineExecutor<>(
                FiniteStateMachine.oneOfMachine(
                        errorMessage -> {
                            throw new ExecutionException(errorMessage);
                        },
                        new ProgramElementTransducer(ProgramElement.WHILE_OPERATOR, this).named("While loop"),
                        new ProgramElementTransducer(ProgramElement.FOR_LOOP, this).named("For loop"),
                        new ProgramElementTransducer(ProgramElement.SWITCH_OPERATOR, this).named("Switch operator"),
                        new ProgramElementTransducer(ProgramElement.INIT_VAR, this).named("Variable initialisation"),
                        new ProgramElementTransducer(ProgramElement.PROCEDURE, this).named("Procedure"),
                        new ProgramElementTransducer(ProgramElement.ANALYZE_VARIABLE, this).named("Produce variable")
                )
        ));

        executors.put(ProgramElement.INIT_VAR, () -> new InitVarExecutor(this));

        executors.put(ProgramElement.ANALYZE_VARIABLE, ProduceVariableExecutor::new);

        executors.put(ProgramElement.PROGRAM, () -> new MachineExecutor<>(
                ProgramMachine.create(this, errorMessage -> {
                    throw new ExecutionException(errorMessage);
                })
        ));

        executors.put(ProgramElement.WHILE_OPERATOR, () -> new WhileOperatorExecutor(this));

        executors.put(ProgramElement.TERNARY_OPERATOR, () -> new TernaryOperatorExecutor(this));

        executors.put(ProgramElement.FOR_LOOP, () -> new ForLoopExecutor(this));

        executors.put(ProgramElement.SWITCH_OPERATOR, () -> new SwitchOperatorExecutor(this));

        executors.put(ProgramElement.READ_VARIABLE, ReadVariableExecutor::new);
    }

    @Override
    public ProgramElementExecutor create(ProgramElement element) {

        Preconditions.checkNotNull(element);

        return executors.get(element).create();
    }

}
