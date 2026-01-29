package pubsub;

public class Message {
    private final String text;
    private final double number;

    public Message(String text) {
        this.text = (text == null) ? "" : text;
        double v;
        try {
            v = Double.parseDouble(this.text);
        } catch (Exception e) {
            v = Double.NaN;
        }
        this.number = v;
    }

    public Message(double number) {
        this.text = Double.toString(number);
        this.number = number;
    }

    public String asText() {
        return text;
    }

    public double asNumber() {
        return number;
    }
}
