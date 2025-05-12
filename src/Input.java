// Represents one “raw” input from the user
public class Input {
    private final String type;    // e.g. "A", "B", "C"
    private final Object payload; // e.g. an Integer, String, custom object…

    public Input(String type, Object payload) {
        this.type = type;
        this.payload = payload;
    }
    public String getType()      { return type; }
    public Object getPayload()   { return payload; }
}
