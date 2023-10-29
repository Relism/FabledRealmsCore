package net.fabledrealms.api.socket;

import io.socket.client.IO;
import io.socket.client.Socket;
import net.fabledrealms.Core;
import net.fabledrealms.events.custom.consoleMessageEvent;
import net.fabledrealms.util.msg;
import net.fabledrealms.events.custom.*;
import net.fabledrealms.events.listeners.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.json.JSONObject;
import java.net.URISyntaxException;

public class socketHandler implements Listener {
    public Core FabledCore;
    private Socket socket;

    public socketHandler(Core main) {
        this.FabledCore = main;
        String serverUrl = FabledCore.getConfig().getString("apiurl");
        try {
            socket = IO.socket(serverUrl);
            connect();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        FabledCore.getServer().getPluginManager().registerEvents(this, FabledCore);
    }

    public void connect() {
        socket.connect();
        authenticate();
        setEventListeners();
    }

    public void disconnect() {
        socket.disconnect();
    }

    private void authenticate() {
        JSONObject authData = new JSONObject();
        authData.put("bearer", FabledCore.getConfig().get("apitoken"));
        socket.emit("authenticate", authData);
    }

    private void setEventListeners() {
        socket.on(Socket.EVENT_CONNECT, args -> {
            msg.log("Connected to the server");
        });

        socket.on(Socket.EVENT_DISCONNECT, args -> {
            msg.log("Disconnected from the server");
        });

        socket.on("authenticated", args -> {
            onAuthenticated();
        });

        socket.on("authentication_failed", args -> {
            onAuthenticationFailed();
        });

        socket.on(Socket.EVENT_CONNECT_ERROR, args -> {
            //
        });
    }

    @EventHandler
    public void onConsoleMessageEvent(consoleMessageEvent event){
        msg.log("received event");
        JSONObject consoleMessage = new JSONObject();
        consoleMessage.put("message", event.getMessage().toString());
        consoleMessage.put("level", event.getLevel().toString());
        socket.emit("console-message", consoleMessage);
    }

    private void onAuthenticated() {
        msg.log("Authentication successful");
    }

    private void onAuthenticationFailed() {
        msg.log("Authentication failed");
    }
}
