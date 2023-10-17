package net.fabledrealms.api;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.util.Map;

//this public interface will let us construct the contexts (api routes)
public interface Context {
    void perform(HttpExchange exchange, Map< String, Object > params, server api) throws IOException;
}

