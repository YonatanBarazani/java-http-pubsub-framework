package servlets;

import pubsub.Message;
import pubsub.Topic;
import pubsub.TopicManagerSingleton;
import server.RequestParser.RequestInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class PublishServlet implements Servlet {

    @Override
    public void handle(RequestInfo ri, OutputStream out) throws IOException {
        String topicName = ri.getParameters().get("topic");

        String val = ri.getParameters().get("message");
        if (val == null) val = ri.getParameters().get("value");

        if (topicName == null || topicName.length() == 0 || val == null || val.length() == 0) {
            write(out, 400, "{\"error\":\"missing parameters: topic and message/value\"}\n");
            return;
        }

        double n;
        try {
            n = Double.parseDouble(val);
        } catch (Exception e) {
            write(out, 400, "{\"error\":\"message/value must be a number\"}\n");
            return;
        }

        Topic t = TopicManagerSingleton.get().getTopic(topicName);
        t.publish(new Message(n));

        write(out, 200, "{\"status\":\"ok\",\"topic\":\"" + esc(topicName) + "\",\"value\":" + n + "}\n");
    }

    private void write(OutputStream out, int code, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        String status = (code == 200) ? "OK" : "Bad Request";

        String head = "HTTP/1.1 " + code + " " + status + "\r\n" +
                "Content-Type: application/json; charset=utf-8\r\n" +
                "Content-Length: " + bytes.length + "\r\n" +
                "Connection: close\r\n" +
                "\r\n";

        out.write(head.getBytes(StandardCharsets.UTF_8));
        out.write(bytes);
    }

    private String esc(String s) {
        return s.replace("\\", "\\\\").replace("\"", "\\\"");
    }

    @Override
    public void close() {
    }
}
