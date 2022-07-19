package src.programStructure.switchoperator;


import src.CharSequenceReader;
import src.Transducer;
import src.runtime.WithContext;
import src.util.ExecutionException;

import java.util.List;


public class KeywordTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final String keyword;

    public KeywordTransducer(String keyword) {
        this.keyword = keyword;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        List<Transducer<O, ExecutionException>> keyword1 = Transducer.keyword(keyword);

        for (Transducer<O, ExecutionException> transducer : keyword1) {

            if (!transducer.doTransition(inputChain, outputChain)) {
                return false;
            }
        }

        return true;
    }
}
