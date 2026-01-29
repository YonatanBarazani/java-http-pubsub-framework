package pubsub;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class TopicManagerSingleton {
    private static final TopicManagerSingleton instance = new TopicManagerSingleton();
    private final Map<String, Topic> topics = new HashMap<>();

    private TopicManagerSingleton() {
    }

    public static TopicManagerSingleton get() {
        return instance;
    }

    public Topic getTopic(String name) {
        if (name == null) name = "";
        Topic t = topics.get(name);
        if (t == null) {
            t = new Topic(name);
            topics.put(name, t);
        }
        return t;
    }

    public Collection<Topic> getTopics() {
        return topics.values();
    }
}
