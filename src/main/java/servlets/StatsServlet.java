package servlets;

import pubsub.Topic;
import pubsub.TopicManagerSingleton;
import server.RequestParser.RequestInfo;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class StatsServlet implements Servlet {

    @Override
    public void handle(RequestInfo ri, OutputStream out) throws IOException {
        String topicName = ri.getParameters().get("topic");
        if (topicName == null || topicName.length() == 0) {
            write(out, 400, "{\"error\":\"missing parameter: topic\"}\n");
            return;
        }

        Topic t = TopicManagerSingleton.get().getTopic(topicName);
        write(out, 200, "{\"topic\":\"" + esc(topicName) + "\",\"messages\":" + t.getCount() + "}\n");
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
