package src.util;

import com.google.common.base.Preconditions;
import src.runtime.ProgramContext;
import src.runtime.WithContext;
import src.type.Value;


import java.util.Optional;
import java.util.function.UnaryOperator;

/**
 * Implementation of {@link WithContext} that is an output chain for machine that execute expressions with unary prefix operator.
 */

public class ProduceVariableContext implements WithContext {

    private final ProgramContext programContext;

    private UnaryOperator<Value> unaryOperator;

    public ProduceVariableContext(ProgramContext programContext) {
        this.programContext = Preconditions.checkNotNull(programContext);
    }

    public void setUnaryOperator(UnaryOperator<Value> unaryOperator) {
        this.unaryOperator = Preconditions.checkNotNull(unaryOperator);
    }

    public Value applyOperator(Value operand) {

        return unaryOperator.apply(Preconditions.checkNotNull(operand));
    }

    public Boolean hasUnaryOperator() {

        return Optional.ofNullable(unaryOperator).isPresent();
    }

    @Override
    public ProgramContext getScriptContext() {
        return programContext;
    }

    @Override
    public boolean isParseOnly() {
        return programContext.isParseOnly();
    }
}
