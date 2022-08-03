package src.programFeatures.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.tahiti.ExecutionException;
import src.tahiti.ProgramElement;
import src.tahiti.ProgramElementExecutor;
import src.tahiti.ProgramFactory;
import src.type.BooleanValueVisitor;
import src.type.Value;


/**
 * Implementation of {@link Transducer} that produce value of loop condition to {@link ForLoopOutputChain}.
 */

class ConditionStatementTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ProgramFactory factory;

    ConditionStatementTransducer(ProgramFactory factory) {

        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        ProgramElementExecutor relationalExecutor = factory.create(ProgramElement.BOOLEAN_EXPRESSION);

        if (!outputChain.isParseOnly()) {
            outputChain.getScriptContext().systemStack().create();
        }

        if (relationalExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            Value conditionValue = outputChain.getScriptContext().systemStack().close().result();

            if (BooleanValueVisitor.isBoolean(conditionValue)) {

                outputChain.setConditionValue(BooleanValueVisitor.read(conditionValue));

                if (!outputChain.getConditionValue()) {

                    outputChain.setParseOnly();
                }

                return true;
            }

            return false;
        }

        if (!outputChain.isParseOnly()) {
            outputChain.getScriptContext().systemStack().close();
        }

        return false;
    }
}