package src.programStructure.switchoperator;

import src.CharSequenceReader;
import src.Transducer;
import src.runtime.WithContext;
import src.util.ExecutionException;


class PermissionTransducer<O extends WithContext> implements Transducer<O, ExecutionException> {

    private final Boolean permission;

    PermissionTransducer(Boolean permission) {
        this.permission = permission;
    }

    @Override
    public boolean doTransition(CharSequenceReader inputChain, O outputChain) throws ExecutionException {

        outputChain.getScriptContext().setParsingPermission(permission);

        return true;
    }
}
