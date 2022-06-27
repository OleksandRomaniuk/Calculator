package src;



public class Interpreter {

    public ProgramResult interpret(BazaScriptProgram code) throws IncorrectProgramException {

        Preconditions.checkNotNull(code);

        Input inputChain = new Input(code.getValue());

        ProgramMemory outputChain = new ProgramMemory();

        MathElementResolverFactory factory = new ScriptElementResolverFactory();

        InterpreterMachine interpreterMachine = InterpreterMachine.create(factory);

        try {
            if (!interpreterMachine.run(inputChain, outputChain)) {

                raiseException(inputChain);
            }
        } catch (ResolvingException | IncorrectProgramException e) {
            raiseException(inputChain);
        }

        return new ProgramResult(outputChain.getOutput().toString());
    }

    private static void raiseException(Input inputChain) throws IncorrectProgramException {
        throw new IncorrectProgramException("Syntax error", inputChain.position());
    }
}
