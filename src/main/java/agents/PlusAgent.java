package agents;

import pubsub.Message;
import pubsub.Topic;
import pubsub.TopicManagerSingleton;

public class PlusAgent implements Agent {

    private final Topic a;
    private final Topic b;
    private final Topic out;

    private boolean hasA;
    private boolean hasB;
    private double va;
    private double vb;

    public PlusAgent(String aTopic, String bTopic, String outTopic) {
        TopicManagerSingleton tm = TopicManagerSingleton.get();
        a = tm.getTopic(aTopic);
        b = tm.getTopic(bTopic);
        out = tm.getTopic(outTopic);

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
            out.publish(new Message(va + vb));
        }
    }
}
