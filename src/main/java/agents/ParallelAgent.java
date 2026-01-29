package agents;

import pubsub.Message;

public class ParallelAgent implements Agent {

    private final Agent inner;

    public ParallelAgent(Agent inner) {
        this.inner = inner;
    }

    @Override
    public void onMessage(String topic, Message msg) {
        inner.onMessage(topic, msg);
    }
}
