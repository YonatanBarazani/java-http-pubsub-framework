package servlets;

import pubsub.Topic;
import pubsub.TopicManagerSingleton;
import server.RequestParser.RequestInfo;
import servlets.Servlet;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;

public class TopicsServlet implements Servlet {

    @Override
    public void handle(RequestInfo ri, OutputStream out) throws IOException {
        Collection<Topic> topics = TopicManagerSingleton.get().getTopics();

        StringBuilder sb = new StringBuilder();
        sb.append("{\"topics\":[");
        boolean first = true;

        java.util.List<String> names = new java.util.ArrayList<>();
        for (Topic t : topics) {
            names.add(t.getName());
        }
        java.util.Collections.sort(names);

        for (int i = 0; i < names.size(); i++) {
            if (i > 0)
                sb.append(",");
            sb.append("\"").append(esc(names.get(i))).append("\"");
        }

        sb.append("]}\n");

        write(out, 200, sb.toString());
    }

    private void write(OutputStream out, int code, String body) throws IOException {
        byte[] bytes = body.getBytes(StandardCharsets.UTF_8);
        String head = "HTTP/1.1 200 OK\r\n" +
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
