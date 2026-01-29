package server;

import servlets.Servlet;
import server.RequestParser.RequestInfo;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;

public class MyHTTPServer extends Thread implements HTTPServer {

    private final int port;
    private volatile boolean running;
    private ServerSocket serverSocket;

    private final Map<String, Servlet> getServlets = new HashMap<>();

    public MyHTTPServer(int port) {
        this.port = port;
    }

    @Override
    public void addServlet(String cmd, String uri, Servlet s) {
        if ("GET".equalsIgnoreCase(cmd)) {
            getServlets.put(uri, s);
        }
    }

    @Override
    public void removeServlet(String cmd, String uri) {
        if ("GET".equalsIgnoreCase(cmd)) {
            getServlets.remove(uri);
        }
    }

    @Override
    public void run() {
        running = true;
        try {
            serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(1000);

            while (running) {
                try {
                    Socket client = serverSocket.accept();
                    handleClient(client);
                } catch (SocketTimeoutException ignored) {}
            }
        } catch (IOException ignored) {}
        close();
    }

    private void handleClient(Socket client) {
        try (Socket c = client) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(c.getInputStream()));
            OutputStream out = c.getOutputStream();

            RequestInfo ri = RequestParser.parseRequest(reader);
            if (ri == null) return;

            Servlet s = longestMatch(getServlets, ri.getUri());
            if (s == null) {
                out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
                return;
            }
            s.handle(ri, out);
            out.flush();
        } catch (IOException ignored) {}
    }

    private Servlet longestMatch(Map<String, Servlet> map, String uri) {
        Servlet best = null;
        int max = -1;
        for (String p : map.keySet()) {
            if (uri.startsWith(p) && p.length() > max) {
                best = map.get(p);
                max = p.length();
            }
        }
        return best;
    }

    @Override
    public void close() {
        running = false;
        try {
            if (serverSocket != null) serverSocket.close();
        } catch (IOException ignored) {}
    }
}
