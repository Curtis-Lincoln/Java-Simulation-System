public class Context {
    private final Engine engine;
    private final SimulationState state;

    public Context(Engine engine, SimulationState state) {
        this.engine = engine;
        this.state = state;
    }

    public Engine engine() { return engine; }
    public SimulationState state() { return state; }
}
