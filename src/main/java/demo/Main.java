package demo;

import agents.BinOpAgent;
import agents.IncAgent;
import agents.PlusAgent;
import server.MyHTTPServer;
import servlets.PublishServlet;
import servlets.StatsServlet;
import servlets.TopicsServlet;

public class Main {
    public static void main(String[] args) {

        new PlusAgent("A", "B", "SUM");
        new IncAgent("SUM", "SUM_PLUS_ONE");
        new BinOpAgent("A", "B", "MUL", (x, y) -> x * y);

        MyHTTPServer server = new MyHTTPServer(8080);

        server.addServlet("GET", "/publish", new PublishServlet());
        server.addServlet("GET", "/stats", new StatsServlet());
        server.addServlet("GET", "/topics", new TopicsServlet());

        server.start();

        System.out.println("Server running on http://localhost:8080");
        System.out.println("Examples:");
        System.out.println("  /publish?topic=A&message=5");
        System.out.println("  /publish?topic=B&message=7");
        System.out.println("  /stats?topic=SUM");
        System.out.println("  /stats?topic=SUM_PLUS_ONE");
        System.out.println("  /stats?topic=MUL");
        System.out.println("  /topics");
    }
}
