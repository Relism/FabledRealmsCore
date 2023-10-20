package net.fabledrealms.api.contexts;

import com.sun.net.httpserver.HttpExchange;
import com.ticxo.modelengine.api.events.ModelRegistrationEvent;
import com.ticxo.modelengine.api.generator.BaseItemEnum;
import com.ticxo.modelengine.api.generator.ModelGenerator;
import com.ticxo.modelengine.api.generator.parser.ModelParser;
import net.fabledrealms.api.Context;
import net.fabledrealms.api.server;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public class resourcePackContext implements Context {
    @Override
    public void perform(HttpExchange exchange, Map<String, Object> params, server api) throws IOException {

    }
}
