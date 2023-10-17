package net.fabledrealms.api.contexts;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.Headers;
import net.fabledrealms.api.Context;
import net.fabledrealms.api.server;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class sseContext implements Context {

    private static List<OutputStream> clients = new ArrayList<>();

    //defines the client-server connection and flags the client as connected.
    @Override
    public void perform(HttpExchange exchange, Map<String, Object> params, server api) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.add("Content-Type", "text/event-stream");
        responseHeaders.add("Connection", "keep-alive");
        responseHeaders.add("X-Powered-By", "Native Application Server");

        exchange.sendResponseHeaders(200, 0);
        OutputStream writer = exchange.getResponseBody();

        clients.add(writer);
    }

    //public method to emit e server-sent event
    public static void sendEvent(String event, JSONObject data) {
        Iterator<OutputStream> iterator = clients.iterator();
        while (iterator.hasNext()) {
            OutputStream client = iterator.next();
            try {
                if (isOutputStreamValid(client)) {
                    sendEvent(client, event, data);
                } else {
                    iterator.remove();
                }
            } catch (IOException e) {
                iterator.remove();
                e.printStackTrace();
            }
        }
    }

    //internal method to handle sse emitment.
    private static void sendEvent(OutputStream writer, String event, JSONObject data) throws IOException {
        String eventData = new JSONObject().put("event", event).put("data", data.toString()).toString() + "\n";
        writer.write(eventData.getBytes());
        writer.flush();
    }

    //checks if the OutputStream is valid, so it won't send data through closed or broken pipes
    private static boolean isOutputStreamValid(OutputStream outputStream) {
        try {
            outputStream.write(new JSONObject().toString().getBytes());
            outputStream.flush();
            return true;
        } catch (IOException e) {
            return false;
        }
    }
}

