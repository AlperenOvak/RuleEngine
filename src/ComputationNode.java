import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class ComputationNode implements RuleNode {
    private final String   id;
    private final List<String> dependsOn;
    private final List<String> next;
    private final Function<List<Object>,Object> computeFn;

    public ComputationNode(String id,
                           List<String> dependsOn,
                           List<String> next,
                           Function<List<Object>,Object> computeFn)
    {
        this.id         = id;
        this.dependsOn  = dependsOn;
        this.next       = next;
        this.computeFn  = computeFn;
    }

    @Override
    public void evaluate(RuleContext ctx, RuleEngine eng) {
        // gather upstream outputs
        List<Object> inputs = new ArrayList<>();
        for (String up : dependsOn) {
            inputs.add(ctx.getOutput(up));
        }
        Object result = computeFn.apply(inputs);
        ctx.setOutput(id, result);
        // fire all next nodes
        for (String n : next) {
            eng.nodeDone(id, n);
        }
    }

    @Override public String getId()              { return id; }
    @Override public List<String> getDependsOn() { return dependsOn; }
    @Override public List<String> getNext()      { return next; }
}
