package src.statement;


import src.fsm.Input;
import src.fsm.Transducer;
import src.ProgramMemory;
import src.impl.fsm.function.FunctionMachine;
import src.impl.fsm.util.FunctionHolder;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolverFactory;
import src.procedure.Procedure;
import src.procedure.ProcedureFactory;

public class ProcedureTransducer implements Transducer<ProgramMemory> {

    private final MathElementResolverFactory factory;

    ProcedureTransducer(MathElementResolverFactory factory) {
        this.factory = factory;
    }

    @Override
    public boolean doTransition(Input inputChain, ProgramMemory outputChain) throws ResolvingException {

        FunctionHolder functionHolder = new FunctionHolder();

        ProcedureFactory procedureFactory = new ProcedureFactory();

        FunctionMachine machine = FunctionMachine.create(factory);

        if (machine.run(inputChain, functionHolder)){

            Procedure procedure = procedureFactory.create(functionHolder.getFunctionName());

            procedure.create(functionHolder.getArguments(), outputChain);

            return true;
        }

        return false;
    }
}
