package net.fabledrealms.events.custom;

import net.fabledrealms.util.msg;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LogEvent;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.time.Instant;
import org.apache.logging.log4j.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class consoleMessageEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final LogEvent logEvent;

    public consoleMessageEvent(LogEvent event){
        this.logEvent = event;
    }

    public Message getMessage(){
        return logEvent.getMessage();
    }

    public Level getLevel(){
        return logEvent.getLevel();
    }

    public String getLoggerName(){
        return logEvent.getLoggerName();
    }

    public Instant getInstant(){
        return logEvent.getInstant();
    }


    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}


