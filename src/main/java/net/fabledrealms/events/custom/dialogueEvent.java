package net.fabledrealms.events.custom;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class dialogueEvent extends Event {
    //emits events to handle response from the OptionHandler
    private static final HandlerList HANDLERS = new HandlerList();
    private final Player player;
    private final String option;

    public dialogueEvent(Player player, String responseOption) {
        this.player = player;
        this.option = responseOption;
    }

    public Player getPlayer() {
        return player;
    }

    public String getResponseOption() {
        return option;
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
