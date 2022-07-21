package src.programStructure.forloop;


import src.CharSequenceReader;
import src.Transducer;
import src.runtime.WithContext;
import src.util.ExecutionException;
import src.util.ProgramElement;
import src.util.ProgramFactory;

public class StatementListTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final ProgramFactory factory;

    public StatementListTransducer(ProgramFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        var executor = factory.create(ProgramElement.PROGRAM);

        return executor.execute(inputChain, outputChain.getScriptContext());
    }
}
