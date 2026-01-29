package agents;

import pubsub.Message;

public interface Agent {
    void onMessage(String topic, Message msg);
}
