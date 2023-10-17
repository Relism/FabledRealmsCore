package net.fabledrealms.api;

import com.sun.net.httpserver.*;
import net.fabledrealms.api.middlewares.authMiddleware;
import java.io.*;
import java.net.InetSocketAddress;
import net.fabledrealms.api.contexts.*;
import net.fabledrealms.util.msg;

public class server {

    public void start(Integer port, String apisecret) {
        try {
            authMiddleware amw = new authMiddleware(apisecret);
            HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
            contextManager ctxManager = new contextManager(server, amw);

            server.createContext("/api", new HttpHandler() {
                @Override
                public void handle(HttpExchange exchange) throws IOException {
                    if(amw.handle(exchange)){
                        replyOK(exchange, "Authenticated");
                    }
                }
            });

            //standardized dynamic contexts
            ctxManager.createContext("/api/events", new sseContext(), true);

            server.setExecutor(null); // creates a default executor
            server.start();
            msg.log("started api on port " + port);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void replyOK(HttpExchange HttpEx, String response) {
        try {
            HttpEx.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            Headers responseHeaders = HttpEx.getResponseHeaders();
            responseHeaders.add("Content-Type", ("application/json"));
            HttpEx.sendResponseHeaders(200, response.length());
            try (OutputStream os = HttpEx.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void replyERROR(HttpExchange HttpEx, String response) {
        try {
            HttpEx.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
            Headers responseHeaders = HttpEx.getResponseHeaders();
            responseHeaders.add("Content-Type", ("application/json"));
            HttpEx.sendResponseHeaders(404, response.length());
            try (OutputStream os = HttpEx.getResponseBody()) {
                os.write(response.getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
