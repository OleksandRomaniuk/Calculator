package src.util;

import com.google.common.base.Preconditions;
import src.CharSequenceReader;
import src.Transducer;
import src.runtime.WithContext;
import src.tahiti.ExecutionException;


import java.util.List;

/**
 * Implementation of {@link Transducer}  for reading keywords such as for example :
 * while , switch , for, case and others
 */
public class KeywordTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final String keyword;

    public KeywordTransducer(String keyword) {
        this.keyword = Preconditions.checkNotNull(keyword);
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        var startPosition = inputChain.position();

        List<Transducer<O, ExecutionException>> keyword = Transducer.keyword(this.keyword);

        for (Transducer<O, ExecutionException> transducer : keyword) {


            if (!transducer.doTransition(inputChain, outputChain)) {

                inputChain.setPosition(startPosition);

                return false;
            }
        }

        return true;
    }
}
