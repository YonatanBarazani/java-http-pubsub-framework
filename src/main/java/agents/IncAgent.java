package agents;

import pubsub.Message;
import pubsub.Topic;
import pubsub.TopicManagerSingleton;

public class IncAgent implements Agent {

    private final Topic in;
    private final Topic out;

    public IncAgent(String inTopic, String outTopic) {
        TopicManagerSingleton tm = TopicManagerSingleton.get();
        in = tm.getTopic(inTopic);
        out = tm.getTopic(outTopic);
        in.subscribe(this);
    }

    @Override
    public void onMessage(String topic, Message msg) {
        if (msg == null) return;
        if (Double.isNaN(msg.asNumber())) return;

        double v = msg.asNumber() + 1;
        out.publish(new Message(v));
    }
}
