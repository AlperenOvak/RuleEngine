import java.util.Arrays;
import java.util.Collections;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        RuleEngine engine = new RuleEngine();
        RuleContext ctx = engine.getContext();

        // 1) load user inputs: 2×A, 1×B, 1×C
        ctx.addInput(new Input("A",  5));
        ctx.addInput(new Input("A", 10));
        ctx.addInput(new Input("B",  7));
        ctx.addInput(new Input("C",  3));

        // 2) define nodes
        //   Node1: sum all A’s
        engine.register(new ComputationNode(
                "sumA",
                Collections.<String>emptyList(),
                Arrays.asList("cond1"),
                inputs -> {
                    // pull from global inputs inside computeFn
                    double s = 0;
                    for (Input in : ctx.getInputsByType("A")) {
                        s += ((Number)in.getPayload()).doubleValue();
                    }
                    return s;
                }
        ));

        //   Node2: check if sumA > 10
        engine.register(new ConditionNode(
                "cond1",
                Arrays.asList("sumA"),
                c -> ((Number)c.getOutput("sumA")).doubleValue() > 10,
                "sumAB",    // true → sumA + B
                "sumAC"     // false → sumA + C
        ));

        //   Node3: sumA + B
        engine.register(new ComputationNode(
                "sumAB",
                Arrays.asList("sumA"),
                Collections.<String>emptyList(),
                inputs -> {
                    double sumA = ((Number)inputs.get(0)).doubleValue();
                    double b    = ((Number)ctx.getInputsByType("B").get(0).getPayload()).doubleValue();
                    return sumA + b;
                }
        ));

        //   Node4: sumA + C
        engine.register(new ComputationNode(
                "sumAC",
                Arrays.asList("sumA"),
                Collections.<String>emptyList(),
                inputs -> {
                    double sumA = ((Number)inputs.get(0)).doubleValue();
                    double c    = ((Number)ctx.getInputsByType("C").get(0).getPayload()).doubleValue();
                    return sumA + c;
                }
        ));

        // 3) run the engine
        engine.run();

        // 4) inspect results
        System.out.println("sumA = " + ctx.getOutput("sumA"));
        System.out.println("sumAB = " + ctx.getOutput("sumAB"));
        System.out.println("sumAC = " + ctx.getOutput("sumAC"));
    }
}