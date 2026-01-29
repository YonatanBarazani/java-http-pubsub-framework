package agents;

import pubsub.Message;
import pubsub.Topic;
import pubsub.TopicManagerSingleton;

import java.util.function.BinaryOperator;

public class BinOpAgent implements Agent {

    private final Topic a;
    private final Topic b;
    private final Topic out;

    private final BinaryOperator<Double> op;

    private boolean hasA;
    private boolean hasB;
    private double va;
    private double vb;

    public BinOpAgent(String aTopic, String bTopic, String outTopic, BinaryOperator<Double> op) {
        TopicManagerSingleton tm = TopicManagerSingleton.get();

        this.a = tm.getTopic(aTopic);
        this.b = tm.getTopic(bTopic);
        this.out = tm.getTopic(outTopic);
        this.op = op;

        a.subscribe(this);
        b.subscribe(this);

        reset();
    }

    private void reset() {
        hasA = false;
        hasB = false;
        va = 0;
        vb = 0;
    }

    @Override
    public void onMessage(String topic, Message msg) {
        if (msg == null) return;
        if (Double.isNaN(msg.asNumber())) return;

        if (topic.equals(a.getName())) {
            va = msg.asNumber();
            hasA = true;
        } else if (topic.equals(b.getName())) {
            vb = msg.asNumber();
            hasB = true;
        } else {
            return;
        }

        if (hasA && hasB) {
            double r = op.apply(va, vb);
            out.publish(new Message(r));
        }
    }
}
