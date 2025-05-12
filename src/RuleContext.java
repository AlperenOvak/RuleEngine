import java.util.*;

// Holds all global inputs and node outputs in one place
public class RuleContext {
    // user-loaded inputs, keyed by type
    private final Map<String, List<Input>> globalInputs = new HashMap<>();
    // nodeId → its computed output
    private final Map<String, Object> nodeOutputs   = new HashMap<>();

    public void addInput(Input in) {
        List<Input> list = globalInputs.get(in.getType());
        if (list == null) {
            list = new ArrayList<>();
            globalInputs.put(in.getType(), list);
        }
        list.add(in);
    }
    public List<Input> getInputsByType(String type) {
        return globalInputs.getOrDefault(type, Collections.emptyList());
    }

    public void setOutput(String nodeId, Object value) {
        nodeOutputs.put(nodeId, value);
    }
    public Object getOutput(String nodeId) {
        return nodeOutputs.get(nodeId);
    }
}
