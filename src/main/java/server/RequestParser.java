package server;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class RequestParser {

    public static RequestInfo parseRequest(BufferedReader reader) throws IOException {
        if (reader == null) return null;

        String reqLine = reader.readLine();
        if (reqLine == null) return null;

        String[] p = reqLine.trim().split("\\s+");
        if (p.length < 2) return null;

        String httpCommand = p[0].trim();
        String uri = p[1].trim();

        String path = uri;
        String query = "";
        int q = uri.indexOf('?');
        if (q >= 0) {
            path = uri.substring(0, q);
            if (q + 1 < uri.length()) query = uri.substring(q + 1);
        }

        String[] uriSegments = splitPath(path);
        Map<String, String> params = new HashMap<>();
        addParams(query, params);

        String line;
        while ((line = reader.readLine()) != null) {
            if (line.length() == 0) break;
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        while (reader.ready()) {
            line = reader.readLine();
            if (line == null || line.length() == 0) break;
            out.write(line.getBytes(StandardCharsets.UTF_8));
            out.write('\n');
        }

        return new RequestInfo(httpCommand, uri, uriSegments, params, out.toByteArray());
    }

    private static String[] splitPath(String path) {
        List<String> segments = new ArrayList<>();
        String[] items = path.split("/");
        for (int i = 0; i < items.length; i++) {
            if (items[i].length() > 0) segments.add(items[i]);
        }
        return segments.toArray(new String[0]);
    }

    private static void addParams(String query, Map<String, String> params) {
        if (query == null || query.length() == 0) return;

        String[] pairs = query.split("&");
        for (int i = 0; i < pairs.length; i++) {
            String s = pairs[i];
            int eq = s.indexOf('=');
            if (eq < 0) continue;

            String key = decode(s.substring(0, eq).trim());
            String val = decode(s.substring(eq + 1).trim());
            if (key.length() > 0) params.put(key, val);
        }
    }

    private static String decode(String s) {
        try {
            return URLDecoder.decode(s, StandardCharsets.UTF_8);
        } catch (Exception e) {
            return s;
        }
    }

    public static class RequestInfo {
        private final String httpCommand;
        private final String uri;
        private final String[] uriSegments;
        private final Map<String, String> parameters;
        private final byte[] content;

        public RequestInfo(String httpCommand, String uri, String[] uriSegments, Map<String, String> parameters, byte[] content) {
            this.httpCommand = httpCommand;
            this.uri = uri;
            this.uriSegments = uriSegments;
            this.parameters = parameters;
            this.content = content;
        }

        public String getHttpCommand() {
            return httpCommand;
        }

        public String getUri() {
            return uri;
        }

        public String[] getUriSegments() {
            return uriSegments;
        }

        public Map<String, String> getParameters() {
            return parameters;
        }

        public byte[] getContent() {
            return content;
        }
    }
}
