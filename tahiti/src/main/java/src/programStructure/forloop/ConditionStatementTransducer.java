package src.programStructure.forloop;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.type.BooleanValueVisitor;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramFactory;


/**
 * Implementation of {@link Transducer} for producing value of loop condition
 */

class ConditionStatementTransducer implements Transducer<ForLoopOutputChain, ExecutionException> {

    private final ProgramFactory factory;

    ConditionStatementTransducer(ProgramFactory factory) {

        this.factory = Preconditions.checkNotNull(factory);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, ForLoopOutputChain outputChain) throws ExecutionException {

        var relationalExecutor = factory.create(ProgramElement.RELATIONAL_EXPRESSION);

        outputChain.getScriptContext().systemStack().create();

        if (relationalExecutor.execute(inputChain, outputChain.getScriptContext())) {

            if (outputChain.isParseOnly()) {
                return true;
            }

            var conditionValue = outputChain.getScriptContext().systemStack().current().result();

            outputChain.getScriptContext().systemStack().close();

            if (BooleanValueVisitor.isBoolean(conditionValue)) {

                outputChain.ConditionValue(BooleanValueVisitor.read(conditionValue));

                if (!outputChain.ConditionValue()) {

                    outputChain.parseOnly();
                }

                return true;
            }

            return false;
        }

        outputChain.getScriptContext().systemStack().close();

        return false;
    }
}