package net.fabledrealms.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class dialogueEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final int option;

    public dialogueEvent(Player player, int option) {
        this.player = player;
        this.option = option;
    }

    public Player getPlayer() {
        return player;
    }

    public int getOption() {
        return option;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    @Override
    @NotNull
    public HandlerList getHandlers() {
        return HANDLERS;
    }
}
