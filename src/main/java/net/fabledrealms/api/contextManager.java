package net.fabledrealms.api;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import net.fabledrealms.api.middlewares.*;

import java.util.HashMap;
import java.util.Map;

public class contextManager {
    private final HttpServer server;
    private final Map<String, ContextInfo> routes = new HashMap<>();
    private final authMiddleware authMiddleware;
    private final server api = new server();

    public contextManager(HttpServer server, authMiddleware authMiddleware) {
        this.server = server;
        this.authMiddleware = authMiddleware;
    }

    //creates a context on a desired path, handled by a desired standardized context class, with authentication on/off
    public void createContext(String path, Context context, boolean authRequired) {
        HttpHandler handler = exchange -> {
            if (authRequired && !authMiddleware.handle(exchange)) {
                return;
            }
            context.perform(exchange, getParams(exchange), api);
        };
        server.createContext(path, handler);
    }

    //returns the HttpExchange parameters as a Map
    private Map<String, Object> getParams(HttpExchange exchange) {
        return (Map<String, Object>) exchange.getAttribute("parameters");
    }

    private static class ContextInfo {
        private final Context context;
        private final boolean authRequired;

        public ContextInfo(Context context, boolean authRequired) {
            this.context = context;
            this.authRequired = authRequired;
        }
    }
}

