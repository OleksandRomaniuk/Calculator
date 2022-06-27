package src.statement;


import src.fsm.Input;
import src.fsm.Transducer;
import src.ProgramMemory;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolverFactory;
import src.initvar.InitVarContext;
import src.initvar.InitVarMachine;

public class InitVarTransducer implements Transducer<ProgramMemory> {

    private final MathElementResolverFactory factory;

    InitVarTransducer(MathElementResolverFactory factory) {
        this.factory = factory;
    }


    @Override
    public boolean doTransition(Input inputChain, ProgramMemory outputChain) throws ResolvingException {

        InitVarMachine variableInitMachine = InitVarMachine.create(factory);

        InitVarContext initVarContext = new InitVarContext();

        return variableInitMachine.run(inputChain, initVarContext);
    }
}
