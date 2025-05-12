import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RuleEngine {
    private final Map<String,RuleNode> nodes       = new HashMap<>();
    private final Map<String,AtomicInteger> countdown = new HashMap<>();
    private final RuleContext ctx = new RuleContext();

    /** Register all nodes first */
    public void register(RuleNode node) {
        nodes.put(node.getId(), node);
        countdown.put(node.getId(),
                new AtomicInteger(node.getDependsOn().size()));
    }

    /** Load all initial inputs before run() */
    public RuleContext getContext() {
        return ctx;
    }

    /** Call after loading inputs: kicks off nodes with zero deps */
    public void run() {
        for (Map.Entry<String, AtomicInteger> e : countdown.entrySet()) {
            if (e.getValue().get()==0) {
                nodes.get(e.getKey()).evaluate(ctx, this);
            }
        }
    }

    /** Called by nodes to signal downstream completion */
    public void nodeDone(String fromNode, String toNodeId) {
        AtomicInteger cnt = countdown.get(toNodeId);
        if (cnt == null) return;  // unknown node?
        if (cnt.decrementAndGet() == 0) {
            nodes.get(toNodeId).evaluate(ctx, this);
        }
    }
}
