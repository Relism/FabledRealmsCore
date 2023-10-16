package net.fabledrealms.api.middlewares;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import net.fabledrealms.api.server;

import java.io.IOException;
import java.util.Objects;

public class authMiddleware {
    private final String apisecret;
    private final server api = new server();

    public authMiddleware(String apiSecret) {
        this.apisecret = apiSecret;
    }

    public boolean handle(HttpExchange exchange) throws IOException {
        Headers headers = exchange.getRequestHeaders();
        String authorizationHeader = headers.getFirst("Authorization");

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring("Bearer ".length());

            if (Objects.equals(token, apisecret)) {
                return true;
            }
        }

        // Authentication failed; send a 401 Unauthorized response
        String response = "Unauthorized";
        api.replyERROR(exchange, response);
        return false;
    }
}

