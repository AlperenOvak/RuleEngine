import java.util.List;

public interface RuleNode {
    /**
     * Called by the engine once all upstream dependencies are satisfied.
     * Should read from ctx.getInputsByType(...) and/or ctx.getOutput(...)
     * and then ctx.setOutput(...) for itself, and schedule downstream nodes.
     */
    void evaluate(RuleContext ctx, RuleEngine engine);

    /** Unique identifier for wiring */
    String getId();

    /** List of node-IDs this node depends on before firing */
    List<String> getDependsOn();

    /** List of node-IDs to trigger once this node has evaluated */
    List<String> getNext();
}
