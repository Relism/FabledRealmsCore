package net.fabledrealms.events.listeners;

import net.fabledrealms.events.custom.consoleMessageEvent;
import net.fabledrealms.util.msg;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.bukkit.Bukkit;

@Plugin(name = "Log4JAppender", category = "Core", elementType = "appender", printObject = true)
class consoleMessageListener extends AbstractAppender {

    public consoleMessageListener() {
        super("Log4JAppender", null,
                PatternLayout.newBuilder()
                        .withPattern("[%d{HH:mm:ss} %level]: %msg")
                        .build(), false);
    }

    @Override
    public boolean isStarted() {
        return true;
    }

    @Override
    public void append(LogEvent event) {
        /*String message = event.getMessage().getFormattedMessage();
        Level level = event.getLevel();

        JSONObject logData = new JSONObject();
        logData.put("message", message);
        logData.put("level", level.toString());*/

        consoleMessageEvent cme = new consoleMessageEvent(event);
        msg.log("calling the event");
        Bukkit.getPluginManager().callEvent(cme);

    }
}
