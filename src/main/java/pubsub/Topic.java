package pubsub;

import java.util.ArrayList;
import java.util.List;

import agents.Agent;

public class Topic {
    private final String name;
    private final List<Agent> subs = new ArrayList<>();
    private final List<Message> messages = new ArrayList<>();

    public Topic(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void subscribe(Agent a) {
        if (a == null) return;
        if (!subs.contains(a)) subs.add(a);
    }

    public void publish(Message m) {
        if (m == null) return;

        messages.add(m);
        for (int i = 0; i < subs.size(); i++) {
            subs.get(i).onMessage(name, m);
        }
    }

    public int getCount() {
        return messages.size();
    }
}
