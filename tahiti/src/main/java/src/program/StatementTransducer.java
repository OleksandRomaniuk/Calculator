package src.program;


import src.fsm.Input;
import src.fsm.Transducer;
import src.ProgramMemory;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolverFactory;
import src.statement.StatementMachine;

public class StatementTransducer implements Transducer<ProgramMemory> {

    private final MathElementResolverFactory factory;

    public StatementTransducer(MathElementResolverFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(Input inputChain, ProgramMemory outputChain) throws ResolvingException {

        StatementMachine machine = StatementMachine.create(factory);

        return machine.run(inputChain, outputChain);
    }
}
