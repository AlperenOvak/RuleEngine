import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class ConditionNode implements RuleNode {
    private final String   id;
    private final List<String> dependsOn;
    private final String   trueNext, falseNext;
    private final Predicate<RuleContext> predicate;

    public ConditionNode(String id,
                         List<String> dependsOn,
                         Predicate<RuleContext> predicate,
                         String trueNext,
                         String falseNext)
    {
        this.id         = id;
        this.dependsOn  = dependsOn;
        this.predicate  = predicate;
        this.trueNext   = trueNext;
        this.falseNext  = falseNext;
    }

    @Override
    public void evaluate(RuleContext ctx, RuleEngine eng) {
        boolean cond = predicate.test(ctx);
        eng.nodeDone(id, cond ? trueNext : falseNext);
    }

    @Override public String getId()                { return id; }
    @Override public List<String> getDependsOn()   { return dependsOn; }
    @Override public List<String> getNext() {
        return Arrays.asList(trueNext, falseNext);
    }
}
