package src.execute;


import src.fsm.Input;
import src.fsm.Transducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import src.ProgramMemory;
import src.impl.fsm.util.ResolvingException;
import src.impl.math.MathElementResolverFactory;
import src.program.ProgramMachine;

public class ProgramTransducer implements Transducer<ProgramMemory> {

    private static final Logger logger = LoggerFactory.getLogger(ProgramTransducer.class);

    private final MathElementResolverFactory factory;

    public ProgramTransducer(MathElementResolverFactory factory) {

        this.factory = factory;
    }


    @Override
    public boolean doTransition(Input inputChain, ProgramMemory outputChain) throws ResolvingException {

        ProgramMachine programMachine = ProgramMachine.create(factory);

        if (logger.isInfoEnabled()){

            logger.info("Working with input chain -> {}", inputChain.toString());
        }

        return programMachine.run(inputChain, outputChain);
    }
}
